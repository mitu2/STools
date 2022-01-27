package runstatic.stools.service.impl

import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileUrlResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import runstatic.stools.configuration.SToolsProperties
import runstatic.stools.logging.useSlf4jLogger
import runstatic.stools.service.WebDocService
import runstatic.stools.service.exception.terminate
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

@Service
class WebDocServiceImpl @Autowired constructor(
    private val restTemplate: RestTemplate,
    private val sToolsProperties: SToolsProperties,
) : WebDocService {

    private val logger = useSlf4jLogger();

    private fun getResourceUrl(type: String) =
        sToolsProperties.webDocResources[type] ?: terminate(HttpStatus.BAD_REQUEST) {
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
    ): InputStream = getDocResource(type, group, artifactId, version, path).inputStream


    override fun getDocResource(
        type: String,
        group: String,
        artifactId: String,
        version: String,
        path: String
    ): Resource {
        val resourceUrl = getResourceUrl(type)
        val groupPath = group.replace(".", "/")
        val reqPath = "${groupPath}/${artifactId}/${version}/${artifactId}-${version}-javadoc.jar";
        val jarPath = "${sToolsProperties.workFolder}/web-doc/${reqPath}"
        val file = File(jarPath)
        with(file.parentFile) {
            !exists() && mkdir()
        }
        if (REQ_CACHE.contains(reqPath)) {
            terminate(HttpStatus.RESET_CONTENT)
        }
        if (!file.exists()) {
            try {
                REQ_CACHE.add(reqPath)
                val url = "${resourceUrl}/${reqPath}"
                restTemplate.execute(url, HttpMethod.GET, {}, { resp ->
                    resp.body.use {
                        file.createNewFile()
                        val fileOutputStream = FileOutputStream(file)
                        it.copyTo(fileOutputStream)
                        fileOutputStream.close()
                    }
                })
            } catch (e: RestClientException) {
                logger.debug(e.message, e)
                terminate()
            } finally {
                REQ_CACHE.remove(reqPath)
            }
        }
        return with(FileUrlResource(ResourceUtils.getURL("jar:file:${jarPath}!/${path}"))) {
            if (!exists()) {
                terminate(HttpStatus.NOT_FOUND)
            }
            this
        }
    }


    companion object {
        val REQ_CACHE: MutableList<String> = Collections.synchronizedList(arrayListOf<String>())
    }

}
