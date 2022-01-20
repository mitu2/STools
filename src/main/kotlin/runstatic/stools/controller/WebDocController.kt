package runstatic.stools.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import runstatic.stools.service.WebDocService
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

    @RequestMapping(path = ["{type}/{group}:{artifactId}:{version}/**"])
    fun docHtml(
        request: HttpServletRequest, response: HttpServletResponse,
        @PathVariable type: String, @PathVariable group: String,
        @PathVariable artifactId: String, @PathVariable version: String,
    ) {
        val servletOutputStream = response.outputStream
        val pathSplit = request.requestURI.split("/${group}:${artifactId}:${version}", "/${group}:${artifactId}")
        var path = if (pathSplit.size > 1) pathSplit[1] else "index.html"

        if (path.startsWith("/")) {
            path = path.substring(1)
        }
        if(path == "/") {
            path ="index.html"
        }
        webDocService.getDocInputStream(type, group, artifactId, version, path)
            .use {
                it.copyTo(servletOutputStream)
                servletOutputStream.flush()
            }
    }

    @RequestMapping(path = ["{type}/{group}:{artifactId}/**"])
    fun docLatestHtml(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @PathVariable type: String,
        @PathVariable group: String,
        @PathVariable artifactId: String
    ) =
        docHtml(request, response, type, group, artifactId, webDocService.getLatestVersion(type, group, artifactId))


}