package ink.metoo.stools.ui.component

import com.github.mvysny.karibudsl.v10.anchor
import com.github.mvysny.karibudsl.v10.div
import com.github.mvysny.karibudsl.v10.image
import com.github.mvysny.karibudsl.v10.text
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
        addClassName("footer")
        div("footer-inner") {
            div("copyright") {
                text("metoo.ink © 2021-2024 沉默")
                div {
                    className = "footer-item-inline"
                    anchor("https://beian.miit.gov.cn", "冀ICP备2021022987号-2") {
                        setTarget("_blank")
                    }
                }
            }

        }
    }

}