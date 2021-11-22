package runstatic.stools.util

import com.vaadin.flow.data.binder.Binder

/**
 *
 * @author chenmoand
 */
inline fun <reified BEAN> BEAN.binder() = Binder(BEAN::class.java)