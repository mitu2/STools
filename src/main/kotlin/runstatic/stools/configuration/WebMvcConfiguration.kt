package runstatic.stools.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import runstatic.stools.logging.useSlf4jLogger


/**
 * @author chenmoand
 * @version 0.1
 */
@Configuration
// @EnableWebMvc
class WebMvcConfiguration @Autowired constructor(
    private val properties: ApplicationProperties
) : WebMvcConfigurer {

    private val logger = useSlf4jLogger()

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
            .allowedOriginPatterns("*.static.run")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**").addResourceLocations(
            "classpath:/static/", "${properties.workFolder}/static/"
        )

        registry.addResourceHandler("/webjars/**").addResourceLocations(
            "classpath:/META-INF/resources/webjars/"
        )
        super.addResourceHandlers(registry)
    }


}