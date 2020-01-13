package com.serversquad.polytech.mailapp.mailapp.repository.email

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant

abstract class AbstractEmailRepository implements EmailRepository {

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
