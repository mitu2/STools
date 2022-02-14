package runstatic.stools.configuration.security

import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.server.ServiceInitEvent
import com.vaadin.flow.server.UIInitEvent
import com.vaadin.flow.server.VaadinServiceInitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import runstatic.stools.configuration.SToolsProperties
import runstatic.stools.ui.view.LoginView
import runstatic.stools.util.SecurityUtils

@Component
class ConfigureUIServiceInitListener @Autowired constructor(
    private val properties: SToolsProperties
) : VaadinServiceInitListener {

    override fun serviceInit(event: ServiceInitEvent) {
        event.source.addUIInitListener { uiEvent: UIInitEvent ->
            val ui = uiEvent.ui
            ui.addBeforeEnterListener { event: BeforeEnterEvent -> beforeEnter(event) }
        }
    }

    /**
     * Reroutes the user if (s)he is not authorized to access the view.
     *
     * @param event
     * before navigation event with event details
     */
    private fun beforeEnter(event: BeforeEnterEvent) {
        if (!SecurityUtils.isUserLoggedIn()) {
            // @see https://vaadin.com/learn/tutorials/securing-your-app-with-spring-security/setting-up-spring-security
//             event.rerouteTo(LoginView::class.java)
        }
    }
}