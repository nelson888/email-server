package com.serversquad.polytech.mailapp.mailapp.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class ApplicationConfiguration {

    //disable cors
    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
            }
        }
    }

    @Bean
    Map<String, String> nameUrlMap(@Value('${mail.messages.body.format.list}') String[] formats) {
        Map<String, String> nameUrlMap = [:]
        formats.each { String url ->
            String name = url.substring(url.lastIndexOf('/') + 1, url.indexOf('.xsd'))
            nameUrlMap.put(name, url)
        }
        return nameUrlMap
    }
}
