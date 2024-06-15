package ink.metoo.stools.configuration.security

import com.vaadin.flow.server.VaadinServletRequest
import com.vaadin.flow.server.VaadinServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.savedrequest.DefaultSavedRequest
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.SavedRequest
import org.springframework.stereotype.Component
import ink.metoo.stools.configuration.SToolsProperties
import ink.metoo.stools.logging.useSlf4jLogger
import ink.metoo.stools.ui.view.admin.LoginView
import ink.metoo.stools.util.SecurityUtils
import ink.metoo.stools.util.SecurityUtils.isFrameworkInternalRequest
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class CustomRequestCache: HttpSessionRequestCache() {

    private val logger = useSlf4jLogger()

    /**
     * {@inheritDoc}
     *
     *
     * If the method is considered an internal request from the framework, we skip
     * saving it.
     *
     * @see SecurityUtils.isFrameworkInternalRequest
     */
    override fun saveRequest(request: HttpServletRequest, response: HttpServletResponse) {
        if (!isFrameworkInternalRequest(request)) {
            super.saveRequest(request, response)
        }
    }

    fun resolveRedirectUrl(): String {
        val savedRequest: SavedRequest = getRequest(
            VaadinServletRequest.getCurrent().httpServletRequest,
            VaadinServletResponse.getCurrent().httpServletResponse
        )
        if (savedRequest is DefaultSavedRequest) {
            val requestURI = savedRequest.requestURI
            // check for valid URI and prevent redirecting to the login view
            if (requestURI != null && requestURI.isNotEmpty() && !requestURI.contains("/${LoginView.ROUTE}")) {
                return if (requestURI.startsWith("/")) requestURI.substring(1) else requestURI
            }
        }

        // if everything fails, redirect to the main view
        return ""
    }
}