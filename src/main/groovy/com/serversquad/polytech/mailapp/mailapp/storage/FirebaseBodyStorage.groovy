package com.serversquad.polytech.mailapp.mailapp.storage

import com.google.cloud.storage.Blob
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredBody
import com.serversquad.polytech.mailapp.mailapp.service.EmailParser
import com.serversquad.polytech.mailapp.mailapp.service.FirebaseStorageService
import groovy.xml.MarkupBuilder
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
        StringWriter writer = new StringWriter()
        MarkupBuilder xml = new MarkupBuilder(writer)
        xml.body(xmlns: "polytech/app5/xm-mail/body/polytech/0.0.1" +
                "",  body.content)
        storageService.store(PREFIX, body.id, writer.toString())
        return body
    }

    @Override
    protected StoredBody parse(Blob blob) {
        String content = new String(blob.getContent())
        return new StoredBody(
                id: blob.name - "$PREFIX/",
                content: new XmlSlurper().parseText(content).text(),
                format: null // TODO use format repository? or map?
        )
    }

    @Override
    protected String fileNameForId(String id) {
        return id
    }
}
