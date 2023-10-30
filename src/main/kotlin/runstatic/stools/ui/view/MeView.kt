package runstatic.stools.ui.view

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
import runstatic.stools.ui.entity.TextFieldState
import runstatic.stools.ui.stye.*
import runstatic.stools.ui.stye.MeStyle.baseStyle
import runstatic.stools.ui.util.pageLayout


/**
 *
 * @author chenmoand
 */
@Route("")
@RouteAlias.Container(
    value = [
        RouteAlias("index"),
        RouteAlias("index.html"),
        RouteAlias("chenmoand"),
        RouteAlias("me")
    ]
)
@PageTitle("沉默程序猿")
@SpringComponent
@UIScope
class MeView : KComposite() {

    private val root = ui {
        pageLayout {
            mainStyle()
            h3("你好, Hello World!")
            p("欢迎你来的本站, 我是沉默, 一名菜鸟程序猿!")
            formLayout {
                baseStyle()
                for (fieldState in FINAL_TEXT_FIELD_STATES) {
                    textField(fieldState.label) {
                        value = fieldState.value
                        isReadOnly = true
                        suffixComponent = button("See", VaadinIcon.EYE.create()) {
                            pointerStyle()
                            inputRightStyle()
                            onLeftClick { open(fieldState.url) }
                        }
                    }
                }
            }
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
            TextFieldState("Blog", "https://blog.static.run"),
            TextFieldState("Email", "chenmoand@outlook.com", "mailto:chenmoand@outlook.com"),
            TextFieldState("Tools", "https://static.run/ui/util-list")
        )


        fun Div.mainStyle() = css {
            marginZeroStyle()
            textAlignCenterStyle()
            width = "400px"
        }

    }

}