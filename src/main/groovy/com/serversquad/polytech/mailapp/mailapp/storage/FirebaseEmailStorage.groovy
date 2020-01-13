package com.serversquad.polytech.mailapp.mailapp.storage

import com.google.cloud.storage.Blob
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.service.EmailParser
import com.serversquad.polytech.mailapp.mailapp.service.FirebaseStorageService
import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

import java.util.stream.Collectors

@Profile("firebase")
@Component
@Slf4j('LOGGER')
class FirebaseEmailStorage {

    private static final String PREFIX = "emails"

    private final FirebaseStorageService storageService
    private final EmailParser emailParser

    FirebaseEmailStorage(FirebaseStorageService storageService, EmailParser emailParser) {
        this.storageService = storageService
        this.emailParser = emailParser
    }

    StoredEmail save(StoredEmail storedEmail) throws IOException {
        storageService.store(PREFIX, storedEmail.uuid + ".xml", emailParser.toString(storedEmail).bytes)
        return storedEmail
    }

    Optional<StoredEmail> getById(int id) {
        try {
            Blob blob = storageService.get(PREFIX, id + ".xml")
            return Optional.ofNullable(blob)
                    .map(this.&toEmail)
        } catch (IOException e) {
            LOGGER.error("Error while retreiving email with id " + id, e)
            return Optional.empty()
        }
    }

    List<StoredEmail> getAll() {
        return storageService.blobStream()
                .filter({ b -> b.getName().contains(PREFIX + "/") && b.getName().endsWith(".xml") })
                .map(this.&toEmail)
                .collect(Collectors.toList())
    }

    private StoredEmail toEmail(Blob blob) {
        return emailParser.parseEmail(blob.getContent())
    }
}
