package runstatic.stools.service.impl

import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileUrlResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import runstatic.stools.configuration.SToolsProperties
import runstatic.stools.logging.debug
import runstatic.stools.logging.useSlf4jLogger
import runstatic.stools.service.WebDocService
import runstatic.stools.service.exception.ServiceNotCompletedException
import runstatic.stools.service.exception.terminate
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.ref.WeakReference
import java.util.WeakHashMap
import java.util.concurrent.ConcurrentHashMap

@Service
class WebDocServiceImpl @Autowired constructor(
    private val sToolsProperties: SToolsProperties,
) : WebDocService {

    private val logger = useSlf4jLogger()
    private val client = OkHttpClient()
    private val cacheVersionMap = WeakHashMap<String, String>()

    private fun getResourceUrl(type: String) =
        sToolsProperties.webDocResources[type] ?: terminate(HttpStatus.BAD_REQUEST) {
            message = "Not support type: $type, Please use ${sToolsProperties.webDocResources.keys}"
        }

    override fun getMavenMetaData(type: String, group: String, artifactId: String): Document {
        val resourceUrl = getResourceUrl(type)
        val url = "${resourceUrl}/${group.replace(".", "/")}/${artifactId}/maven-metadata.xml"
        logger.debug { "try get versionList, request: $url" }
        return Jsoup.connect(url).get()
    }

    override fun getLatestVersion(type: String, group: String, artifactId: String): String {
        try {
            return cacheVersionMap.computeIfAbsent("$type:$group:$artifactId") {
                getMavenMetaData(type, group, artifactId).select("metadata > versioning > latest").html()
            }
        } catch (e: ServiceNotCompletedException) {
            throw e
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
        type: String, group: String, artifactId: String, version: String, path: String
    ): InputStream = getDocResource(type, group, artifactId, version, path).inputStream


    override fun getDocResource(
        type: String, group: String, artifactId: String, version: String, path: String
    ): Resource {
        val resourceUrl = getResourceUrl(type)
        val groupPath = group.replace(".", "/")
        val reqPath = "${groupPath}/${artifactId}/${version}/${artifactId}-${version}-javadoc.jar"
        val jarPath = "${sToolsProperties.workFolder}/web-doc/${reqPath}"
        val file = File(jarPath)
        with(file.parentFile) {
            // note: use mkdir() bug
            !exists() && mkdirs()
        }

        if (!file.exists() || REQ_CACHE_MAP[reqPath] != null) {
            try {
                synchronized(REQ_CACHE_MAP.computeIfAbsent(reqPath) { Any() }) {
                    if (file.exists()) {
                        return@synchronized
                    }
                    // note:
                    client.newCall(
                        Request.Builder().get().url("${resourceUrl}/${reqPath}").build()
                    ).execute().body?.use {
                        it.byteStream().use { inputStream ->
                            file.createNewFile()
                            val fileOutputStream = FileOutputStream(file)
                            inputStream.copyTo(fileOutputStream)
                            fileOutputStream.close()
                        }
                    } ?: terminate(HttpStatus.NOT_FOUND)
                }
            } catch (e: Exception) {
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
        val REQ_CACHE_MAP: ConcurrentHashMap<String, Any> = ConcurrentHashMap()
    }


}

