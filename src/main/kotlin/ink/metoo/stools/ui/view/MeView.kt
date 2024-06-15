package ink.metoo.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouteAlias
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import ink.metoo.stools.ui.entity.TextFieldState
import ink.metoo.stools.ui.stye.*
import ink.metoo.stools.ui.stye.MeStyle.baseStyle
import ink.metoo.stools.ui.util.pageLayout


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
@PageTitle("论我也一样的重要性")
@SpringComponent
@UIScope
class MeView : KComposite() {

    private val root = ui {
        pageLayout {
            mainStyle()
            h3("你好, Hello World!")
            p("网站正在维护")
        }
    }

    fun open(url: String, type: String = "_self") {
        root.ui.ifPresent { ui ->
            ui.access {
                ui.page.open(url, type)
            }
        }
    }



    companion object {

        val FINAL_TEXT_FIELD_STATES = listOf(
            TextFieldState("Github", "https://github.com/mitu2"),
            TextFieldState("Blog", "https://i.metoo.ink"),
            TextFieldState("Email", "chenmoand@outlook.com", "mailto:chenmoand@outlook.com"),
        )


        fun Div.mainStyle() = css {
            marginZeroStyle()
            textAlignCenterStyle()
            width = "400px"
        }

    }

}