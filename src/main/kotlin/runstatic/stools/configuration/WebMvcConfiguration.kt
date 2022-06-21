package runstatic.stools.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


/**
 * @author chenmoand
 * @version 0.1
 */
@Configuration
// @EnableWebMvc
class WebMvcConfiguration @Autowired constructor(
    private val properties: SToolsProperties,
) : WebMvcConfigurer {


    @Bean
    @Profile("dev")
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.addAllowedOriginPattern("*") // 修改为添加而不是设置，* 最好改为实际的需要，我这是非生产配置，所以粗暴了一点
        configuration.addAllowedMethod("*")            // 修改为添加而不是设置
        configuration.addAllowedHeader("*")       // 这里很重要，起码需要允许 Access-Control-Allow-Origin
        configuration.allowCredentials = true
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }

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