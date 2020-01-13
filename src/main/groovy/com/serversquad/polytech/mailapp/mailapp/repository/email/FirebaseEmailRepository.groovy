package com.serversquad.polytech.mailapp.mailapp.repository.email

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.storage.EmailStorage

class FirebaseEmailRepository extends AbstractEmailRepository {

    private final EmailStorage emailStorage

    FirebaseEmailRepository(EmailStorage emailStorage) {
        this.emailStorage = emailStorage
    }

    @Override
    StoredEmail saveEmail(StoredEmail email) throws IOException {
        if (email.uuid == null) {
            email.uuid = UUID.randomUUID().toString()
        }
        return emailStorage.save(email)
    }

    @Override
    List<StoredEmail> getAll() {
        return emailStorage.getAll()
    }

    @Override
    Optional<StoredEmail> getByUUID(String uuid) {
        return Optional.ofNullable(emailStorage.getAll().find { it.uuid == uuid })
    }

}
