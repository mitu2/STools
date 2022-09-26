package runstatic.stools.configuration.webdoc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import runstatic.stools.logging.info
import runstatic.stools.logging.useSlf4jLogger

/**
 * @author chenmoand
 */
@Configuration
class WebDocConfiguration @Autowired constructor(
    private val webDocResourceResolver: WebDocResourceResolver
) : WebMvcConfigurer {


    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler(*WebDocResourceResolver.PATH_PATTERNS)
            // note: fix cache bug
            .resourceChain(false)
            .addResolver(webDocResourceResolver)
        logger.info { "load WebDocResourceResolver" }
    }

    companion object {
        private val logger = WebDocConfiguration.useSlf4jLogger()
    }
}
