package com.serversquad.polytech.mailapp.mailapp.repository.email.body

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredBody
import com.serversquad.polytech.mailapp.mailapp.storage.FirebaseBodyStorage

class FirebaseBodyRepository implements BodyRepository {

    private final FirebaseBodyStorage bodyStorage

    FirebaseBodyRepository(FirebaseBodyStorage bodyStorage) {
        this.bodyStorage = bodyStorage
    }

    @Override
    StoredBody getById(String id) {
        return bodyStorage.getById(id).orElse(null)
    }

    @Override
    StoredBody save(String content, String format) {
        StoredBody storedBody = new StoredBody(content: content, format: format)
        return bodyStorage.save(storedBody)
    }
}
