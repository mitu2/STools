package runstatic.stools.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.hibernate.validator.constraints.Range
import org.hibernate.validator.constraints.URL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import runstatic.stools.service.ShortUrlService
import runstatic.stools.util.useSlf4jLogger
import javax.annotation.security.PermitAll
import javax.validation.constraints.NotBlank

/**
 *
 * @author chenmoand
 */
@Api(value = "http工具", tags = ["http"])
@RestController
@RequestMapping(path = ["httpTool"])
@PermitAll
class HttpToolController @Autowired constructor(
    private val shortUrlService: ShortUrlService
) {

    val log = useSlf4jLogger()

    @ApiOperation(value = "重定向跳转接口", code = 302)
    @RequestMapping(path = ["redirect"])
    @Suppress("SpringMVCViewInspection")
    fun redirect(@Validated @ModelAttribute param: HttpToolParams.RedirectParam): ModelAndView = ModelAndView().apply {
        val cycle = param.cycle
        if (cycle == null || cycle <= 0) {
            viewName = "redirect:${param.to}"
        } else {

            viewName = "redirect:redirect"
            model["cycle"] = cycle - 1
            model["to"] = param.to
        }
    }

    @ApiOperation(value = "短网址跳转接口", code = 302)
    @GetMapping(path = ["redirect/{router}"])
    @Suppress("SpringMVCViewInspection")
    fun redirectShortUrl(@PathVariable router: String): ModelAndView = ModelAndView().apply {
        viewName = try {
            val shortUrl = shortUrlService.findShortUrlByRouter(router)
            "redirect:${shortUrl.url}"
        } catch (e: Exception) {
            log.debug(e.message, e)
            "redirect:404"
        }
    }

}

sealed interface HttpToolParams {

    data class RedirectParam(
        @field:Range(min = 0, max = 15)
        var cycle: Int? = 0,
        @field:NotBlank
        @field:URL
        var to: String
    ) : HttpToolParams

}