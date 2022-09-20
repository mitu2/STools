package runstatic.stools.ui.stye

import com.vaadin.flow.component.HasStyle
import com.vaadin.flow.component.button.Button

/**
 *
 * @author chenmoand
 */
fun HasStyle.pointerStyle() = css {
    style["cursor"] = "pointer"
}

fun Button.inputRightStyle() = css {
    style["margin"] = "0 -5px 0 0"
}