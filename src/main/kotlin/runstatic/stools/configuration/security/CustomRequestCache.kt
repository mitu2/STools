package runstatic.stools.configuration.security

import runstatic.stools.util.SecurityUtils.isFrameworkInternalRequest
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import runstatic.stools.util.SecurityUtils

internal class CustomRequestCache : HttpSessionRequestCache() {
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
}