package runstatic.stools.controller

import org.hibernate.validator.constraints.Range
import org.hibernate.validator.constraints.URL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import runstatic.stools.logging.useSlf4jLogger
import javax.annotation.security.PermitAll
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

    data class RedirectParam(
        @field:Range(min = 0, max = 15)
        var cycle: Int? = 0,
        @field:NotBlank
        @field:URL
        var to: String
    )

}
