package ink.metoo.stools.ui.stye

import com.github.mvysny.karibudsl.v10.KFormLayout
import com.vaadin.flow.component.formlayout.FormLayout

/**
 * @author chenmoand
 */
object MeStyle {

    fun KFormLayout.baseStyle() = css {
        width = "400px"
        marginZeroStyle()
        responsiveSteps = listOf(
            FormLayout.ResponsiveStep("0", 1)
        )
    }

}