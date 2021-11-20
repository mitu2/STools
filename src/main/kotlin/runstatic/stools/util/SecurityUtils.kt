package runstatic.stools.util

import javax.servlet.http.HttpServletRequest
import com.vaadin.flow.shared.ApplicationConstants
import com.vaadin.flow.server.HandlerHelper
import com.vaadin.flow.server.ServletHelper
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.AnonymousAuthenticationToken
import java.util.stream.Stream

object SecurityUtils {

    /**
     * Tests if the request is an internal framework request. The test consists of
     * checking if the request parameter is present and if its value is consistent
     * with any of the request types know.
     *
     * @param request
     * [HttpServletRequest]
     * @return true if is an internal framework request. False otherwise.
     */
    fun isFrameworkInternalRequest(request: HttpServletRequest): Boolean {
        val parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER)
        return (parameterValue != null && HandlerHelper.RequestType
            .values()
            .iterator().asSequence()
            .any { r: HandlerHelper.RequestType -> r.identifier == parameterValue })
    }

    /**
     * Tests if some user is authenticated. As Spring Security always will create an [AnonymousAuthenticationToken]
     * we have to ignore those tokens explicitly.
     */
    val isUserLoggedIn: Boolean
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            return (authentication != null && authentication !is AnonymousAuthenticationToken
                    && authentication.isAuthenticated)
        }
}