package runstatic.stools.ui.stye

import com.vaadin.flow.component.HasStyle


inline fun <T : HasStyle> T.css(block: T.() -> Unit): T {
    block()
    return this
}

