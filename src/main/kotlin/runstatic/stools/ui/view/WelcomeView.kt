package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouteAlias
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.material.Material
import runstatic.stools.ui.component.PageFooter
import runstatic.stools.util.pointer


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
@PageTitle("沉默的小玩意 - static.run")
@CssImport("./css/Welcome.css")
@SpringComponent
@UIScope
//@Theme(value = Material::class)
class WelcomeView : KComposite() {


    private val root = ui {
        verticalLayout {
            setId("app")
            formLayout("main") {
                h3("你好, Hello World!")
//                image("/image/cat.gif")
                p("欢迎你来的本站, 我是沉默, 一名菜鸟程序猿!")
                textField("Github") {
                    value = "https://github.com/mitu2"
                    isReadOnly = true
                    button("OPEN") {
                        pointer()
                        addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST)
                        onLeftClick {
                            open(this@textField.value)
                        }
                    }
                }
                textField("Blog") {
                    value = "https://blog.static.run"
                    isReadOnly = true
                    button("OPEN") {
                        pointer()
                        addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST)
                        onLeftClick {
                            open(this@textField.value)
                        }
                    }

                }
                textField("Email") {
                    value = "chenmoand@gmail.com"
                    isReadOnly = true
                    button("OPEN") {
                        pointer()
                        addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST)
                        onLeftClick {
                            open("mailto:${this@textField.value}")
                        }
                    }
                }
                textField("QQ Group") {
                    value = "558504614"
                    isReadOnly = true
                }

            }
            add(PageFooter())
        }
    }

    fun open(url: String, type: String = "_self") {
        root.element.executeJs("window.open(\$0, \$1)", url, type)
    }

}