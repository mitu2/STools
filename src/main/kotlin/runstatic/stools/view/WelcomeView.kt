package runstatic.stools.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouteAlias
import com.vaadin.flow.spring.annotation.UIScope


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
@CssImport("css/Welcome.css")
@UIScope
class WelcomeView : KComposite() {


    private val root = ui {
        div {
            setId("main")
            h3("你好, Hello World!")
            image("http://blog.static.run/upload/2021/09/hai-af9830ec61494d388f95f0f0cdb31517.gif")
            p("欢迎你来的本站, 我是沉默, 一名菜鸟程序猿!")
            p("Github: ") {
                anchor("https://github.com/mitu2") {
                    setTarget("_blank")
                }
            }
            p("Blog: ") {
                anchor("http://blog.static.run") {
                    setTarget("_blank")
                }
            }
            p("Email: ") {
                anchor("mailto:chenmoand@gmail.com", "chenmoand@gmail.com")
            }
            p("QQ Group: 558504614 欢迎来玩") {
            }

            div("footer") {
                div("copyright") {
                    text("STATIC.RUN © 2021 沉默")
                }
                div {
                    text("由")
                    anchor("https://github.com/mitu2/STools", "STools") {
                        setTarget("_blank")
                    }
                    text("强力驱动")
                }
                div {
                    span {
                        className = "footer-item-inline"
                        image("http://blog.static.run//upload/2021/11/gongan-a56bae00c42c4dccbc5eecdd5c2d0aee.png") {
                            className = "jinghui"
                        }
                        anchor("http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=13100202000682", "冀公网安备 13100202000682号") {
                            setTarget("_blank")
                        }
                    }
                    span {
                        className = "footer-item-inline"
                        anchor("http://beian.miit.gov.cn", "冀ICP备2021022987号") {
                            setTarget("_blank")
                        }
                    }
                }
            }
        }
    }

}