package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouteAlias
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import runstatic.stools.ui.component.PageFooter


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
@PageTitle("沉默 - static.run")
@CssImport("./css/Welcome.css")
@SpringComponent
@UIScope
class WelcomeView : KComposite() {


    private val root = ui {
        div {
            setId("main")
            h3("你好, Hello World!")
            image("https://blog.static.run/upload/2021/09/hai-af9830ec61494d388f95f0f0cdb31517.gif")
            p("欢迎你来的本站, 我是沉默, 一名菜鸟程序猿!")
            p("Github: ") {
                anchor("https://github.com/mitu2") {
                    setTarget("_blank")
                }
            }
            p("Blog: ") {
                anchor("https://blog.static.run") {
                    setTarget("_blank")
                }
            }
            p("Email: ") {
                anchor("mailto:chenmoand@gmail.com", "chenmoand@gmail.com")
            }
            p("QQ Group: 558504614 欢迎来玩") {
            }
            add(PageFooter())
        }
    }

}