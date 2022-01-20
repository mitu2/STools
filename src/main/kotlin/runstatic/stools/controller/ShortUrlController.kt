package runstatic.stools.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import runstatic.stools.service.ShortUrlService

/**
 *
 * @author chenmoand
 */
@Controller
class ShortUrlController @Autowired constructor(
    private val shortUrlService: ShortUrlService
) {

    @GetMapping(path = ["s/{router}"])
    fun redirectByRouter(@PathVariable router: String): String {
        val url = shortUrlService.findShortUrlByRouter(router)?.url ?: "/404"
        return "redirect:$url"
    }

}