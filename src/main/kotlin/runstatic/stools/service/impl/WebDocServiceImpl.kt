package runstatic.stools.service.impl

import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import runstatic.stools.configuration.SToolsProperties
import runstatic.stools.service.WebDocService
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*
import java.util.jar.JarFile

@Service
class WebDocServiceImpl @Autowired constructor(
    private val restTemplate: RestTemplate,
    private val properties: SToolsProperties,
) : WebDocService {

    override fun getLatestVersion(type: String, group: String, artifactId: String): String =
        when (type) {
            "maven" -> getMavenLatestVersion(group, artifactId)
            else -> throw RuntimeException("not found $type : $group")
        }


    fun getMavenLatestVersion(group: String, artifactId: String): String {
        val url = "${MAVEN_BASE_URL}/${group.replace(".", "/")}/${artifactId}/maven-metadata.xml"
        val document = Jsoup.connect(url).get()
        return document.select("metadata > versioning > latest").html()
    }


    override fun getDocInputStream(
        type: String,
        group: String,
        artifactId: String,
        version: String,
        path: String
    ): InputStream =
        when (type) {
            "maven" -> getMavenDocInputStream(group, artifactId, version, path)
            else -> throw RuntimeException("not found $type : $group")
        }

    fun getMavenDocInputStream(group: String, artifactId: String, version: String, path: String): InputStream {
        val groupPath = group.replace(".", "/")
        val reqPath = "${groupPath}/${artifactId}/${version}/${artifactId}-${version}-javadoc.jar";
        val file = File("${properties.workFolder}/web-doc/${reqPath}")
        val dir = file.parentFile
        if (!dir.exists()) {
            dir.mkdirs()
        }
        if (!file.exists() && !REQ_CACHE.contains(reqPath)) {
            REQ_CACHE.add(reqPath)
            try {
                val url = "${MAVEN_BASE_URL}/${reqPath}"
                restTemplate.execute(url, HttpMethod.GET, { req ->
                    // req.headers.set("Range", String.format("bytes=%d-%d", 0, Long.MAX_VALUE))
                }, { resp ->
                    resp.body.use {
                        file.createNewFile()
                        val fileOutputStream = FileOutputStream(file)
                        it.copyTo(fileOutputStream)
                        fileOutputStream.close()
                    }
                })
            } finally {
                REQ_CACHE.remove(reqPath)
            }

        }
        return JarFile(file).run {
            getInputStream(getJarEntry(path))
        }
    }

    companion object {
        const val MAVEN_BASE_URL = "https://maven.aliyun.com/repository/central"
        val REQ_CACHE: MutableList<String> = Collections.synchronizedList(arrayListOf<String>())
    }

}
