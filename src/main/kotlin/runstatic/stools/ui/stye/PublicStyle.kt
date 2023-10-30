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

fun HasStyle.showStyle() = css {
    if (style["display"] == "none") {
        style.remove("display")
    }
}

fun HasStyle.hideStyle() = css {
    style["display"] = "none"
}

fun HasStyle.marginZeroStyle() = css {
    margin = "0px auto"
}

fun HasStyle.textAlignCenterStyle() = css {
    style["text-align"] = "center"
}

fun  HasStyle.paddingZeroStyle() = css {
    padding = "0px"
}

var HasStyle.margin: String?
    get() = style["margin"]
    set(value) {
        style["margin"] = value
    }


var HasStyle.padding: String?
    get() = style["padding"]
    set(value) {
        style["value"] = value
    }
