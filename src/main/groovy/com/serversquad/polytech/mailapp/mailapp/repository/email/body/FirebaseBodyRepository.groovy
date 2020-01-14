package com.serversquad.polytech.mailapp.mailapp.repository.email.body

import com.serversquad.polytech.mailapp.mailapp.model.mail.Email
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredBody
import com.serversquad.polytech.mailapp.mailapp.storage.FirebaseBodyStorage
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Profile("firebase")
@Repository
class FirebaseBodyRepository implements BodyRepository {

    private final FirebaseBodyStorage bodyStorage

    FirebaseBodyRepository(FirebaseBodyStorage bodyStorage) {
        this.bodyStorage = bodyStorage
    }

    @Override
    StoredBody getById(Email email, String id) {
        return bodyStorage.getById(id).orElse(null)
    }

    @Override
    StoredBody save(String mailUuid, String content, String format) {
        StoredBody storedBody = new StoredBody(id: 'xmmessage_' + UUID.randomUUID().toString(),
                content: content, format: format)
        return bodyStorage.save(storedBody)
    }
}
