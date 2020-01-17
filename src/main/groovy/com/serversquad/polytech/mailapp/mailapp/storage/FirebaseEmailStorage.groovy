package com.serversquad.polytech.mailapp.mailapp.storage

import com.google.cloud.storage.Blob
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.service.EmailParser
import com.serversquad.polytech.mailapp.mailapp.service.FirebaseStorageService
import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Slf4j('LOGGER')
@Profile("firebase")
@Component
class FirebaseEmailStorage extends FirebaseStorage<StoredEmail, Integer> {

    private static final String PREFIX = "emails"

    FirebaseEmailStorage(FirebaseStorageService storageService, EmailParser emailParser) {
        super(PREFIX, storageService, emailParser)
    }

    @Override
    StoredEmail save(StoredEmail storedEmail) {
        Blob blob = storageService.store(PREFIX, storedEmail.uuid + ".xml", emailParser.toString(storedEmail))
        LOGGER.info("Saved email ${storedEmail.uuid} at  ${blob.name}")
        return storedEmail
    }

    @Override
    protected StoredEmail parse(Blob blob) {
        return emailParser.parseEmail(blob.getContent())
    }

    @Override
    protected String fileNameForId(Integer id) {
        return "${id}.xml"
    }

}
