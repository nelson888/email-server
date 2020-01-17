package com.serversquad.polytech.mailapp.mailapp.storage

import com.google.cloud.storage.Blob
import com.serversquad.polytech.mailapp.mailapp.model.mail.BodyRef
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredBody
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.message.StoredMessage
import com.serversquad.polytech.mailapp.mailapp.repository.bodyformat.BodySchemaRepository
import com.serversquad.polytech.mailapp.mailapp.service.EmailParser
import com.serversquad.polytech.mailapp.mailapp.service.FirebaseStorageService
import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

import java.nio.charset.StandardCharsets

@Slf4j('LOGGER')
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
        Blob blob = storageService.store(PREFIX, body.id, body.content)
        LOGGER.info("Saved body ${body.id} at ${blob.name}")
        return body
    }


    @Override
    protected StoredBody parse(Blob blob) {
        return new StoredBody(
                id: blob.name - "$PREFIX/",
                content: new String(blob.getContent(), StandardCharsets.UTF_8),
                format: null
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
