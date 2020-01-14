package com.serversquad.polytech.mailapp.mailapp.storage

import com.google.cloud.storage.Blob
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredBody
import com.serversquad.polytech.mailapp.mailapp.service.EmailParser
import com.serversquad.polytech.mailapp.mailapp.service.FirebaseStorageService
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("firebase")
@Component
class FirebaseBodyStorage extends FirebaseStorage<StoredBody, String> {

    private static final String PREFIX = "bodies"

    FirebaseBodyStorage(FirebaseStorageService storageService, EmailParser emailParser) {
        super(PREFIX, storageService, emailParser)
    }

    @Override
    StoredBody save(StoredBody body) {
        storageService.store(PREFIX, body.id, body.content.bytes)
        return body
    }

    @Override
    protected StoredBody parse(Blob blob) {
        return new StoredBody(
                id: blob.name - "$PREFIX/",
                content: new XmlSlurper().parseText(new String(blob.getContent())).text(),
                format: null // TODO use format repository? or map?
        )
    }

    @Override
    protected String fileNameForId(String id) {
        return id
    }
}
