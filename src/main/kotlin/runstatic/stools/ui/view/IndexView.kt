package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.br
import com.github.mvysny.karibudsl.v10.text
import com.vaadin.flow.component.UI
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouteAlias
import com.vaadin.flow.server.PWA
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import runstatic.stools.logging.info
import runstatic.stools.logging.useSlf4jLogger
import runstatic.stools.util.pageLayout
import java.util.*
import kotlin.concurrent.schedule

/**
 *
 * @author chenmoand
 */

@Route("")
@RouteAlias.Container(
    value = [
        RouteAlias("index"),
        RouteAlias("index.html"),
    ]
)
@PageTitle("Project Stools Readme - static.run")
@SpringComponent
@UIScope
// @PWA(name = "Github Project Stools", shortName = "Stools")
class IndexView : KComposite() {

    private val logger = useSlf4jLogger()

    private val root = ui {
        pageLayout {
            text("under construction...")
            br()
            text("redirect author page...")
        }
    }

    init {
        UI.getCurrent().page.executeJs("setTimeout(() => window.location.href='/ui/chenmoand', 1500)")
    }


}