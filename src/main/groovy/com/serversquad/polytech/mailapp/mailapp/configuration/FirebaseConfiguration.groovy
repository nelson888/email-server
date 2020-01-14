package com.serversquad.polytech.mailapp.mailapp.configuration

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.Bucket
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.StorageClient
import com.serversquad.polytech.mailapp.mailapp.repository.email.EmailRepository
import com.serversquad.polytech.mailapp.mailapp.repository.email.FirebaseEmailRepository
import com.serversquad.polytech.mailapp.mailapp.repository.email.body.BodyRepository
import com.serversquad.polytech.mailapp.mailapp.repository.email.body.FirebaseBodyRepository
import com.serversquad.polytech.mailapp.mailapp.storage.FirebaseBodyStorage
import com.serversquad.polytech.mailapp.mailapp.storage.FirebaseEmailStorage
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("firebase")
@Configuration
class FirebaseConfiguration {

    @Value('${firebase.storage.bucket}')
    private String storageBucket

    @Value('${firebase.database.url}')
    private String databaseUrl

    @Bean
    StorageClient storageClient() throws IOException {
        InputStream fConfig = FirebaseConfiguration.class.getResourceAsStream("/firebase-config.json")
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(fConfig))
                    .setDatabaseUrl(databaseUrl)
                    .build()
            FirebaseApp.initializeApp(options)
            return StorageClient.getInstance()
        } finally {
            fConfig.close()
        }
    }

    @Bean
    Bucket bucket(StorageClient storageClient) {
        return storageClient.bucket(storageBucket)
    }


}
