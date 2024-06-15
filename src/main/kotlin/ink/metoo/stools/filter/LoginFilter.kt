package ink.metoo.stools.filter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import ink.metoo.stools.configuration.SToolsProperties
import ink.metoo.stools.util.SecurityUtils
import java.net.URLEncoder
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 *
 * @author chenmoand
 */
@Component
class LoginFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (request !is HttpServletRequest || response !is HttpServletResponse) {
            chain.doFilter(request, response)
            return
        }

        val requestUri = request.requestURI

        val cond = "GET".contentEquals(request.method) &&
                requestUri.startsWith("/admin") &&
                !SecurityUtils.isUserLoggedIn()

        if (cond) {
            val params = URLEncoder.encode(request.queryString?.run { "?$this" } ?: "", "UTF-8")
            response.sendRedirect("/login?from=$requestUri$params")
            return
        }
        chain.doFilter(request, response)

    }


}