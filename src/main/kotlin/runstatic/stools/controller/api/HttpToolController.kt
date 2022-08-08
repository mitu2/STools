package runstatic.stools.controller.api

import org.hibernate.validator.constraints.Range
import org.hibernate.validator.constraints.URL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.support.RequestContextUtils
import runstatic.stools.logging.info
import runstatic.stools.logging.useSlf4jLogger
import runstatic.stools.util.HttpRequestUtil
import javax.annotation.security.PermitAll
import javax.servlet.http.HttpServletRequest
import javax.validation.constraints.NotBlank

/**
 *
 * @author chenmoand
 */
@RestController
@RequestMapping(path = ["api/http"])
@PermitAll
class HttpToolController @Autowired constructor(
) {

    val log = useSlf4jLogger()

    @RequestMapping(path = ["redirect"])
    @Suppress("SpringMVCViewInspection")
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

    class RedirectParam(
        @field:Range(min = 0, max = 15)
        val cycle: Int? = 0,
        @field:URL
        @field:NotBlank
        val to: String
    )

    @RequestMapping("ping")
    fun ping(request: HttpServletRequest): String {
        log.info {
            "http ping ip: " + HttpRequestUtil.getClientIP(request)
        }
        return "pong"
    }


}
