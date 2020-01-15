package com.serversquad.polytech.mailapp.mailapp.storage

import com.google.cloud.storage.Blob
import com.serversquad.polytech.mailapp.mailapp.model.mail.BodyRef
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredBody
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.message.StoredMessage
import com.serversquad.polytech.mailapp.mailapp.repository.bodyformat.BodySchemaRepository
import com.serversquad.polytech.mailapp.mailapp.service.EmailParser
import com.serversquad.polytech.mailapp.mailapp.service.FirebaseStorageService
import groovy.xml.MarkupBuilder
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

import java.nio.charset.StandardCharsets

@Profile("firebase")
@Component
class FirebaseBodyStorage extends FirebaseStorage<StoredBody, String> {

    private static final String PREFIX = "bodies"

    private final BodySchemaRepository bodySchemaRepository

    FirebaseBodyStorage(FirebaseStorageService storageService, EmailParser emailParser, BodySchemaRepository bodySchemaRepository) {
        super(PREFIX, storageService, emailParser)
        this.bodySchemaRepository = bodySchemaRepository
    }

    @Override
    StoredBody save(StoredBody body) {
        StringWriter writer = new StringWriter()
        MarkupBuilder xml = new MarkupBuilder(writer)
        xml.mkp.xmlDeclaration(version: "1.0", encoding: "UTF-8")
        xml.body(xmlns: "polytech/app5/xm-mail/body/polytech/0.0.1" +
                "",  body.content)
        storageService.store(PREFIX, body.id, writer.toString())
        return body
    }


    @Override
    protected StoredBody parse(Blob blob) {
        return new StoredBody(
                id: blob.name - "$PREFIX/",
                content: new String(blob.getContent(), StandardCharsets.UTF_8),
                format: null // TODO use format repository? or map?
        )
    }

    @Override
    protected String fileNameForId(String id) {
        return id
    }

    Optional<StoredBody> getById(StoredEmail email, String id) {
        List<BodyRef> refs = email.historic.messages.collect {
            it as StoredMessage
        }.collect {
            it.bodyRef
        }
        Optional<StoredBody> optBody = getById(id)
        if (optBody.present) {
            StoredBody body = optBody.get()
            body.format = refs.find {it.id == body.id }.format
        }
        return optBody
    }
}
