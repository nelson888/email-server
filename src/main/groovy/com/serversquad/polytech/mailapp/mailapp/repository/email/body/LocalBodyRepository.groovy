package com.serversquad.polytech.mailapp.mailapp.repository.email.body

import com.serversquad.polytech.mailapp.mailapp.model.mail.Email
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredBody
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.message.StoredMessage
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

import java.nio.file.Path

@Slf4j('LOGGER')
@Profile("local")
@Repository
class LocalBodyRepository extends AbstractBodyRepository {

    private final String rootInPath
    private final String rootOutPath

    LocalBodyRepository(@Value('${local.storage.bodies.root.in.path}') String rootInPath,
                        @Value('${local.storage.bodies.root.out.path}') String rootOutPath) {
        this.rootInPath = rootInPath
        this.rootOutPath = rootOutPath
    }

    @Override
    StoredBody getById(Email email, String id) {
        File file = Path.of(rootInPath, email.uuid, id).toFile()
        if (!file.exists()) {
            return null
        }
        String format = email.historic.messages
                .collect { it as StoredMessage }
                .find { it.bodyRef.id == id }
                .bodyRef.format
        return new StoredBody(id: id, content: file.text, format: format)
    }

    @Override
    StoredBody save(String mailUuid, String content, String format) {
        File directory = new File(rootInPath, mailUuid)
        if (!directory.exists() && !directory.mkdir()) {
            throw new RuntimeException("Couldn't create directory")
        }
        String id = "xmmessage_" + UUID.randomUUID().toString()
        File file = new File(directory, id)
        file.text = xmlContent(content, format)
        LOGGER.info("Saved body for mail $mailUuid at  ${file.path}")
        return new StoredBody(id: id, format: format, content: content)
    }
}
