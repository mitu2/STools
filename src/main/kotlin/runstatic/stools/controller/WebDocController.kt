package runstatic.stools.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import runstatic.stools.service.WebDocService


/**
 *
 * @author chenmoand
 */
@Controller
@RequestMapping(path = ["web-doc"])
class WebDocController @Autowired constructor(
    private val webDocService: WebDocService
) {

    @RequestMapping(path = ["{type}/{group}:{artifactId}:{version}"])
    fun safeDocHtml(
        @PathVariable type: String, @PathVariable group: String,
        @PathVariable artifactId: String, @PathVariable version: String
    ) = "redirect:/web-doc/${type}/${group}:${artifactId}:${version}/index.html"


    @RequestMapping(path = ["{type}/{group}:{artifactId}"])
    fun safeDocLatestHtml(
        @PathVariable type: String, @PathVariable group: String,
        @PathVariable artifactId: String
    ): String {
        if (artifactId.contains(":")) {
            return "redirect:/web-doc/${type}/${group}:${artifactId}/index.html"
        }
        return "redirect:/web-doc/${type}/${group}:${artifactId}:${webDocService.getLatestVersion(type, group, artifactId)}/index.html"
    }

}