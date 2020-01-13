package com.serversquad.polytech.mailapp.mailapp.configuration

import com.serversquad.polytech.mailapp.mailapp.repository.email.EmailRepository
import com.serversquad.polytech.mailapp.mailapp.repository.email.LocalStorageEmailRepository
import com.serversquad.polytech.mailapp.mailapp.service.EmailParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("local")
@Configuration
class LocalConfiguration {

    @Bean
    EmailRepository emailRepository(EmailParser emailParser) {
        return new LocalStorageEmailRepository(emailParser)
    }

}
