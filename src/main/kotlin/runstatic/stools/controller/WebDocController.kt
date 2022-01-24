package runstatic.stools.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import runstatic.stools.service.WebDocService
import java.net.URI
import java.net.URLConnection
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.activation.MimetypesFileTypeMap
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 *
 * @author chenmoand
 */
@Controller
@RequestMapping(path = ["web-doc"])
class WebDocController @Autowired constructor(
    private val webDocService: WebDocService,
) {

    @RequestMapping(path = ["{type}/{group}:{artifactId}:{version}"])
    fun safeDocHtml(
        @PathVariable type: String, @PathVariable group: String,
        @PathVariable artifactId: String, @PathVariable version: String
    ) = "redirect:/web-doc/${type}/${group}:${artifactId}:${version}/index.html"

    @RequestMapping(path = ["{type}/{group}:{artifactId}:{version}/**"])
    fun docHtml(
        request: HttpServletRequest, response: HttpServletResponse,
        @PathVariable type: String, @PathVariable group: String,
        @PathVariable artifactId: String, @PathVariable version: String,
    ) {
        val servletOutputStream = response.outputStream
        val pathSplit = request.requestURI.split("/${group}:${artifactId}:${version}", "/${group}:${artifactId}")
        var path = if (pathSplit.size > 1) pathSplit[1] else "index.html"

        if (path == "/") {
            path = "index.html"
        }
        if (path.startsWith("/")) {
            path = path.substring(1)
        }
        webDocService.getDocInputStream(type, group, artifactId, version, path)
            .use {
                it.copyTo(servletOutputStream)
                response.flushBuffer()
                servletOutputStream.flush()
            }
    }


    @RequestMapping(path = ["{type}/{group}:{artifactId}"])
    fun safeDocLatestHtml(
        @PathVariable type: String, @PathVariable group: String,
        @PathVariable artifactId: String
    ) = "redirect:/web-doc/${type}/${group}:${artifactId}/index.html"

    @RequestMapping(path = ["{type}/{group}:{artifactId}/**"])
    fun docLatestHtml(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @PathVariable type: String,
        @PathVariable group: String,
        @PathVariable artifactId: String
    ) = docHtml(request, response, type, group, artifactId, webDocService.getLatestVersion(type, group, artifactId))


}