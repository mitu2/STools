package runstatic.stools.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


/**
 *
 * @author chenmoand
 */
@Controller
@RequestMapping(path = ["web-doc"])
class WebDocController @Autowired constructor() {

    @RequestMapping(path = ["{type}/{group}:{artifactId}:{version}"])
    fun safeDocHtml(
        @PathVariable type: String, @PathVariable group: String,
        @PathVariable artifactId: String, @PathVariable version: String
    ) = "redirect:/web-doc/${type}/${group}:${artifactId}:${version}/index.html"


    @RequestMapping(path = ["{type}/{group}:{artifactId}"])
    fun safeDocLatestHtml(
        @PathVariable type: String, @PathVariable group: String,
        @PathVariable artifactId: String
    ) = "redirect:/web-doc/${type}/${group}:${artifactId}/index.html"


}