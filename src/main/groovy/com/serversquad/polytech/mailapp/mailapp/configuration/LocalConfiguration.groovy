package com.serversquad.polytech.mailapp.mailapp.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("local")
@Configuration
class LocalConfiguration {

    @Bean
    File emailRootIn(@Value('${local.storage.mails.root.in.path}') String path) {
        return checkRoot(path)
    }

    @Bean
    File emailRootOut(@Value('${local.storage.mails.root.out.path}') String path) {
        return checkRoot(path)
    }

    private static File checkRoot(String path) {
        File root = new File(path)
        if (!root.exists() || !root.isDirectory()) {
            throw new RuntimeException("${root.path} isn't a directory")
        }
        return root
    }

}
