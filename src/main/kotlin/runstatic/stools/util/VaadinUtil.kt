package runstatic.stools.util

import com.vaadin.flow.component.HasValue
import com.vaadin.flow.component.button.Button
import kotlin.reflect.KProperty

/**
 *
 * @author chenmoand
 */

class VaadinProp<T : Any>(
    initValue: T,
    private val component: HasValue<*, T>
) {

    init {
        component.value = initValue
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return component.value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        component.value = value
    }
}


fun Button.pointer() {
    style["cursor"] = "pointer"
}

fun Button.inputRight() {
    style["margin"] = "0 -5px 0 0"
}



