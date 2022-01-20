package runstatic.stools.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import runstatic.stools.controller.ControllerPosition
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.oas.annotations.EnableOpenApi
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

/**
 *
 * @author chenmoand
 */
@Configuration
@EnableOpenApi
class SwaggerConfiguration @Autowired constructor(
    private val properties: SToolsProperties,
    private val buildProperties: BuildProperties
) {

    @Bean
    fun docker(): Docket = Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage(ControllerPosition.position))
        .build()

    @Bean
    fun apiInfo(): ApiInfo = ApiInfoBuilder()
        .title(buildProperties.name)
        .description("Project Stools by Chenmoand")
        .version(buildProperties.version)
        .contact(Contact("Chenmoand", properties.baseUrl, "chenmoand@outlook.com"))
        .build()

}