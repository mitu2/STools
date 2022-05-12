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
import java.util.concurrent.ConcurrentHashMap

@Service
class WebDocServiceImpl @Autowired constructor(
    private val restTemplate: RestTemplate,
    private val sToolsProperties: SToolsProperties,
) : WebDocService {

    private val logger = useSlf4jLogger()

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
            logger.debug(e.message, e)
            terminate(HttpStatus.valueOf(e.statusCode)) {
                message = e.message ?: "Error"
            }
        } catch (e: Exception) {
            logger.debug(e.message, e)
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
            // note: use mkdir() bug
            !exists() && mkdirs()
        }
        if (!file.exists() || REQ_CACHE_MAP[reqPath] != null) {
            try {
                synchronized(REQ_CACHE_MAP.computeIfAbsent(reqPath) { PathLockObject(it) }) {
                    if (file.exists()) {
                        return@synchronized
                    }
                    restTemplate.execute("${resourceUrl}/${reqPath}", HttpMethod.GET, {}, { resp ->
                        resp.body.use {
                            file.createNewFile()
                            val fileOutputStream = FileOutputStream(file)
                            it.copyTo(fileOutputStream)
                            fileOutputStream.close()
                        }
                    })
                }
            } catch (e: RestClientException) {
                logger.debug(e.message, e)
                terminate()
            } finally {
                REQ_CACHE_MAP.remove(reqPath)
            }
        }
        return FileUrlResource(ResourceUtils.getURL("jar:file:${jarPath}!/${path}")).apply {
            if (!exists()) {
                terminate(HttpStatus.NOT_FOUND)
            }
        }
    }


    companion object {
        val REQ_CACHE_MAP: ConcurrentHashMap<String, PathLockObject> = ConcurrentHashMap()
    }

    data class PathLockObject(val path: String)

}
