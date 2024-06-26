package ink.metoo.stools.ui.util

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.HasValue
import com.vaadin.flow.component.html.Div
import ink.metoo.stools.ui.component.PageLayout
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

fun HasComponents.pageLayout(
    className: String? = null,
    initCallback: Div.() -> Unit = {}
): PageLayout = PageLayout(className, initCallback).also { this + it }


operator fun <C : HasComponents> C.plus(component: Component) = apply { add(component) }

operator fun <C : HasComponents> C.plus(text: String) = apply { add(text) }

fun <T: HasComponents> T.updateDOM(block: T.() -> Unit) {
    removeAll()
    block()
}







