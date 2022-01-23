package runstatic.stools.service.impl

import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import runstatic.stools.configuration.SToolsProperties
import runstatic.stools.service.WebDocService
import runstatic.stools.service.exception.terminate
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*
import java.util.jar.JarFile

@Service
class WebDocServiceImpl @Autowired constructor(
    private val restTemplate: RestTemplate,
    private val sToolsProperties: SToolsProperties,
) : WebDocService {

    private fun getResourceUrl(type: String) = sToolsProperties.webDocResources[type] ?: terminate(HttpStatus.BAD_REQUEST) {
        message = "Not support type: $type, Please use ${sToolsProperties.webDocResources.keys}"
    }

    override fun getLatestVersion(type: String, group: String, artifactId: String): String {
        val resourceUrl = getResourceUrl(type)
        val url = "${resourceUrl}/${group.replace(".", "/")}/${artifactId}/maven-metadata.xml"
        try {
            val document = Jsoup.connect(url).get()
            return document.select("metadata > versioning > latest").html()
        } catch (e: HttpStatusException) {
            terminate(HttpStatus.valueOf(e.statusCode)) {
                message = e.message ?: "Error"
            }
        } catch (e: Exception) {
            terminate {
                message = e.message ?: "Error"
            }
        }
    }

    override fun getDocInputStream(
        type: String,
        group: String,
        artifactId: String,
        version: String,
        path: String
    ): InputStream {
        val resourceUrl = getResourceUrl(type)
        val groupPath = group.replace(".", "/")
        val reqPath = "${groupPath}/${artifactId}/${version}/${artifactId}-${version}-javadoc.jar";
        val file = File("${sToolsProperties.workFolder}/web-doc/${reqPath}")
        with (file.parentFile) {
            !exists() && mkdir()
        }

        if (!file.exists()) {
            if (REQ_CACHE.contains(reqPath)) {
                terminate(HttpStatus.RESET_CONTENT)
            }
            REQ_CACHE.add(reqPath)
            try {
                val url = "${resourceUrl}/${reqPath}"
                restTemplate.execute(url, HttpMethod.GET, {}, { resp ->
                    if (resp.statusCode == HttpStatus.NOT_FOUND) {
                        terminate(HttpStatus.NOT_FOUND) {
                            message = "Not Found $reqPath"
                        }
                    }
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
            getInputStream(
                getJarEntry(path) ?: terminate(HttpStatus.NOT_FOUND)
            )
        }
    }


    companion object {
        val REQ_CACHE: MutableList<String> = Collections.synchronizedList(arrayListOf<String>())
    }

}
