package runstatic.stools.util

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.data.binder.Binder

/**
 *
 * @author chenmoand
 */
inline fun <reified BEAN> BEAN.binder() = Binder(BEAN::class.java)


fun Button.pointer(): Unit {
    style["cursor"] = "pointer"
}



