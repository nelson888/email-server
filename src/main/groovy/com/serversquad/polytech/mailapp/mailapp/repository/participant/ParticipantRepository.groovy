package com.serversquad.polytech.mailapp.mailapp.repository.participant

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import com.serversquad.polytech.mailapp.mailapp.repository.email.EmailRepository
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Repository

@Repository
@Slf4j('LOGGER')
class ParticipantRepository {


    private List<Participant> participants

    ParticipantRepository(EmailRepository emailRepository) {
        participants = emailRepository.getAll()
        .collectMany { StoredEmail email -> email.participants }
        .unique()
        .toList()
        LOGGER.info("Participants loaded")
        LOGGER.info("List of all participants $participants")
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
