package com.serversquad.polytech.mailapp.mailapp.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * Configuration for swagger
 * doc url: http://localhost:8080/swagger-ui.html
 */

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    ApiInfo info() {
        return new ApiInfo(
                "Email Server",
                "API to handle an email app",
                "1.0",
                "Terms of service",
                new Contact("Server SQUAAAAAAAD", "no URL", "no email"),
                "MIT License", "some url", Collections.emptyList())
    }

    @Bean
    Docket api(ApiInfo info) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(info)
                .ignoredParameterTypes(MetaClass.class) // added to ignore metaclasses
    }

}
