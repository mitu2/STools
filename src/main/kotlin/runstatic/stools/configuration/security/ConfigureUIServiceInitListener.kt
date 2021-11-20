package runstatic.stools.configuration.security

import com.vaadin.flow.server.VaadinServiceInitListener
import com.vaadin.flow.server.ServiceInitEvent
import com.vaadin.flow.server.UIInitEvent
import com.vaadin.flow.router.BeforeEnterEvent
import org.springframework.stereotype.Component
import runstatic.stools.util.SecurityUtils

@Component
class ConfigureUIServiceInitListener : VaadinServiceInitListener {
    override fun serviceInit(event: ServiceInitEvent) {
        event.source.addUIInitListener { uiEvent: UIInitEvent ->
            val ui = uiEvent.ui
            ui.addBeforeEnterListener { event: BeforeEnterEvent -> beforeEnter(event) } // (2)
        }
    }

    /**
     * Reroutes the user if (s)he is not authorized to access the view.
     *
     * @param event
     * before navigation event with event details
     */
    private fun beforeEnter(event: BeforeEnterEvent) {
        if (!SecurityUtils.isUserLoggedIn) {
            // TDOD https://vaadin.com/learn/tutorials/securing-your-app-with-spring-security/setting-up-spring-security
//            event.rerouteTo(LoginView::class.java)
        }
    }
}