package com.serversquad.polytech.mailapp.mailapp.storage

import com.google.cloud.storage.Blob
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredBody
import com.serversquad.polytech.mailapp.mailapp.service.EmailParser
import com.serversquad.polytech.mailapp.mailapp.service.FirebaseStorageService

class FirebaseBodyStorage extends FirebaseStorage<StoredBody> {

    private static final String PREFIX = "body"

    FirebaseBodyStorage(FirebaseStorageService storageService, EmailParser emailParser) {
        super(PREFIX, storageService, emailParser)
    }

    @Override
    StoredBody save(StoredBody body) {
        storageService.store(PREFIX, body.id + ".xml", body.content.bytes)
        return body
    }

    @Override
    protected StoredBody parse(Blob blob) {
        return new StoredBody(
                id: blob.name - ".xml",
                content: new XmlSlurper().parseText(new String(blob.getContent())).text()
        )
    }
}
