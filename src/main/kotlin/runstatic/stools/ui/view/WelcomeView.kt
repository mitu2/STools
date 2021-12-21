package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouteAlias
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import runstatic.stools.ui.component.PageFooter
import runstatic.stools.util.inputRight
import runstatic.stools.util.pageLayout
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
@CssImport("./css/welcome.css")
@SpringComponent
@UIScope
class WelcomeView : KComposite() {


    private val root = ui {
        pageLayout {
            addClassName("welcome")
            formLayout {
                h3("你好, Hello World!")
                p("欢迎你来的本站, 我是沉默, 一名菜鸟程序猿!")
                textField("Github") {
                    value = "https://github.com/mitu2"
                    isReadOnly = true
                    suffixComponent = button("OPEN") {
                        pointer()
                        inputRight()
                        onLeftClick {
                            open(this@textField.value)
                        }
                    }
                }
                textField("Blog") {
                    value = "https://blog.static.run"
                    isReadOnly = true
                    suffixComponent = button("OPEN") {
                        pointer()
                        inputRight()
                        onLeftClick {
                            open(this@textField.value)
                        }
                    }

                }
                textField("Email") {
                    value = "chenmoand@gmail.com"
                    isReadOnly = true
                    suffixComponent = button("OPEN") {
                        pointer()
                        inputRight()
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
        }
    }

    fun open(url: String, type: String = "_blank") {
        root.element.executeJs("window.open(\$0, \$1)", url, type)
    }

}