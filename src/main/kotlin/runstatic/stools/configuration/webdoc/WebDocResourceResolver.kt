package runstatic.stools.configuration.webdoc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.servlet.resource.ResourceResolver
import org.springframework.web.servlet.resource.ResourceResolverChain
import runstatic.stools.service.WebDocService
import runstatic.stools.service.exception.terminate
import javax.servlet.http.HttpServletRequest

/**
 *
 * @author chenmoand
 */
@Component
class WebDocResourceResolver @Autowired constructor(
    private val webDocService: WebDocService
) : ResourceResolver {

    override fun resolveResource(
        request: HttpServletRequest?,
        requestPath: String,
        locations: MutableList<out Resource>,
        chain: ResourceResolverChain
    ): Resource? {
        if (request == null) {
            return null
        }
        val params = PATH_MATCHER.extractUriTemplateVariables(PATTERN_1, request.requestURI)
        val type = params["type"] ?: terminate()
        val group = params["group"] ?: terminate()
        val artifactId = params["artifactId"] ?: terminate()
        var version = params["version"]
        if(version.isNullOrBlank()) {
            version = webDocService.getLatestVersion(type, group, artifactId)
        }
        val pathSplit = request.requestURI.split("/${group}:${artifactId}:${version}", "/${group}:${artifactId}")
        var path = if (pathSplit.size > 1) pathSplit[1] else "index.html"

        if (path == "/") {
            path = "index.html"
        }
        if (path.startsWith("/")) {
            path = path.substring(1)
        }
        return webDocService.getDocResource(type, group, artifactId, version, path)
    }

    override fun resolveUrlPath(
        resourcePath: String,
        locations: MutableList<out Resource>,
        chain: ResourceResolverChain
    ): String? = chain.resolveUrlPath(resourcePath, locations)

    companion object {
        private const val PATTERN_1 = "/web-doc/{type}/{group}:{artifactId}:{version}/**"
        private const val PATTERN_2 = "/web-doc/{type}/{group}:{artifactId}/**"
        private val PATH_MATCHER = AntPathMatcher()
        val PATH_PATTERNS = arrayOf(PATTERN_1, PATTERN_2)
    }
}