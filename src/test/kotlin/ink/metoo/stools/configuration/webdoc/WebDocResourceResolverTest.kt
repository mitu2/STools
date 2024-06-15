package ink.metoo.stools.configuration.webdoc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.util.AntPathMatcher

/**
 *
 * @author chenmoand
 */
internal class WebDocResourceResolverTest {

    @Test
    fun testAntPathMatcher() {
        val matcher = AntPathMatcher()

        val variables = matcher.extractUriTemplateVariables(
            "/web-doc/{type}/{group}:{artifactId}:{version}/**",
            "/web-doc/maven/commons-cli:commons-cli:beta/index.html"
        )

        assertEquals(variables["version"], "beta")
    }

}