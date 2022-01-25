package runstatic.stools.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 *
 * @author chenmoand
 */
@Controller
class UiController {

    @GetMapping
    fun redirect() = "redirect:ui"

}