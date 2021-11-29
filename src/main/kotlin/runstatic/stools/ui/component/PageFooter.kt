package runstatic.stools.ui.component

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.HasStyle
import com.vaadin.flow.component.Tag
import com.vaadin.flow.component.dependency.CssImport

/**
 *
 * @author chenmoand
 */
@Tag(Tag.DIV)
@CssImport("./css/footer.css")
class PageFooter : Component(), HasComponents, HasStyle {

    init {
        element.classList.add("footer")
        div("footer-inner") {
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
                    image("/image/police-badge.png", "police-badge") {
                        className = "police-badge"
                    }
                    anchor(
                        "http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=13100202000682",
                        "冀公网安备 13100202000682号"
                    ) {
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