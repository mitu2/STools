// package runstatic.stools.configuration
//
// import org.springframework.context.annotation.Bean
// import org.springframework.context.annotation.Configuration
// import runstatic.stools.controller.ControllerPosition
// import springfox.documentation.builders.ApiInfoBuilder
// import springfox.documentation.builders.RequestHandlerSelectors
// import springfox.documentation.oas.annotations.EnableOpenApi
// import springfox.documentation.service.ApiInfo
// import springfox.documentation.service.Contact
// import springfox.documentation.spi.DocumentationType
// import springfox.documentation.spring.web.plugins.Docket
// import springfox.documentation.swagger2.annotations.EnableSwagger2
//
// /**
//  *
//  * @author chenmoand
//  */
// // @Configuration
// // @EnableOpenApi
// class SwaggerConfiguration {
//
//     @Bean
//     fun docker(): Docket = Docket(DocumentationType.SWAGGER_2)
//         .apiInfo(apiInfo())
//         .select()
//         .apis(RequestHandlerSelectors.basePackage(ControllerPosition.position))
//         .build()
//
//     @Bean
//     fun apiInfo(): ApiInfo = ApiInfoBuilder()
//         .title("Stools")
//         .description("Project Stools by Chenmoand")
//         .version("1.0")
//         .contact(Contact("Chenmoand", "http://blog.static.run", "chenmoand@outlook.com"))
//         .build()
//
// }