package ink.metoo.stools.controller.api

import ink.metoo.stools.controller.param.RedirectParam
import ink.metoo.stools.logging.info
import ink.metoo.stools.logging.useSlf4jLogger
import ink.metoo.stools.util.HttpRequestUtil
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import javax.annotation.security.PermitAll
import javax.servlet.http.HttpServletRequest

/**
 *
 * @author chenmoand
 */
@RestController
@RequestMapping(path = ["api/http"])
@PermitAll
class HttpToolController {

    val log = useSlf4jLogger()

    @RequestMapping(path = ["redirect"])
    fun redirect(@Validated @ModelAttribute param: RedirectParam): ModelAndView = ModelAndView().apply {
        val cycle = param.cycle
        if (cycle == null || cycle <= 0) {
            viewName = "redirect:${param.to}"
        } else {
            viewName = "redirect:redirect"
            model["cycle"] = cycle - 1
            model["to"] = param.to
        }
    }

    @RequestMapping("ping")
    fun ping(request: HttpServletRequest): String {
        log.info {
            "http ping ip: " + HttpRequestUtil.getClientIP(request)
        }
        return "pong"
    }


}
