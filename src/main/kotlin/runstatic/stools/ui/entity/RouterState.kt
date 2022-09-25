package runstatic.stools.ui.entity

import com.vaadin.flow.component.Component
import com.vaadin.flow.router.RouteParameters
import kotlin.reflect.KClass

/**
 * @author chenmoand
 */
data class RouterState(
    val label: String,
    val target: KClass<out Component>,
    val params: RouteParameters = RouteParameters.empty()
)