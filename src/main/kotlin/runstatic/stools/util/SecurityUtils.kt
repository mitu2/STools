package runstatic.stools.util

import com.vaadin.flow.server.HandlerHelper
import com.vaadin.flow.shared.ApplicationConstants
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.http.HttpServletRequest

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
    fun isUserLoggedIn(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return (authentication != null && authentication !is AnonymousAuthenticationToken
                && authentication.isAuthenticated)
    }

}