package com.serversquad.polytech.mailapp.mailapp.repository.participant

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import com.serversquad.polytech.mailapp.mailapp.repository.email.EmailRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class ParticipantRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantRepository.class)

    private List<Participant> participants

    ParticipantRepository(EmailRepository emailRepository) {
        participants = emailRepository.getAll()
        .collectMany { StoredEmail email -> email.participants }
        .unique()
        .toList()
        LOGGER.info("Participants loaded")
        LOGGER.info(participants.toString())
    }

    List<Participant> getAll() {
        return participants
    }

    Optional<Participant> getById(String id) {
        return Optional.ofNullable(
                participants.find {Participant p -> p.id == id }
        )
    }

    Optional<Participant> getByName(String name) {
        return Optional.ofNullable(
                participants.find {Participant p -> p.name == name }
        )
    }
}
