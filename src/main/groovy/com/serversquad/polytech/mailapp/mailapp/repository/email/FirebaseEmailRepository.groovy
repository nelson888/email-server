package com.serversquad.polytech.mailapp.mailapp.repository.email

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.storage.EmailStorage

import java.util.concurrent.atomic.AtomicInteger

class FirebaseEmailRepository implements EmailRepository {

    private final EmailStorage emailStorage
    private final AtomicInteger idGenerator = new AtomicInteger()

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
    Optional<StoredEmail> getById(int id) {
        return emailStorage.getById(id)
    }

    @Override
    List<StoredEmail> getAllByExpeditor(String expeditor) {
        //TODO get All emails with getAll function:
        // then filter to keep only emails with the given expeditor
        return null
    }

    @Override
    List<StoredEmail> getAllByParticipant(String email) {
        //TODO get All emails with getAll function:
        // then filter to keep only emails that has the given email in
        // one of the participants (see StoredEmail.participants)
        // Participant.id correspond to the email
        return null
    }
}
