package com.serversquad.polytech.mailapp.mailapp.repository.email

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
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
    Optional<StoredEmail> getByUUID(String uuid) {
        return Optional.ofNullable(emailStorage.getAll().find { it.uuid == uuid })
    }

    @Override
    List<StoredEmail> getAllByExpeditor(String emittor) {
        List<StoredEmail> emails = []
        for (StoredEmail email : getAll()) {
            if(email.getExpeditor()==emittor){
                emails.add(email);
            }
        }
        return emails
    }

    @Override
    List<StoredEmail> getAllByParticipantId(String id) {
        List<StoredEmail> emails = []
        for (StoredEmail email : getAll()) {
            for (Participant participant : email.participants){
                if(participant.id==id){
                    emails.add(email)
                }
            }
        }
        return emails
    }

    @Override
    List<StoredEmail> getAllByParticipantName(String name) {
        List<StoredEmail> emails = []
        for (StoredEmail email : getAll()) {
            for (Participant participant : email.participants){
                if(participant.name==name){
                    emails.add(email)
                }
            }
        }
        return emails
    }
}
