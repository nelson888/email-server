package com.serversquad.polytech.mailapp.mailapp.repository.group

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.StoredGroup
import com.serversquad.polytech.mailapp.mailapp.repository.email.EmailRepository
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Repository

@Repository
@Slf4j('LOGGER')
class GroupRepository {

    private List<StoredGroup> groups

    GroupRepository(EmailRepository emailRepository) {
        groups = emailRepository.getAll()
        .collectMany { StoredEmail email -> email.groups }
        .unique()
        .toList()
        LOGGER.info("Groups loaded")
        LOGGER.info("List of all groups: $groups")
    }

    List<StoredGroup> getAll() {
        return groups
    }

    Optional<StoredGroup> getById(String id) {
        return Optional.ofNullable(
                groups.find {StoredGroup g -> g.id == id }
        )
    }

    Optional<StoredGroup> getByName(String name) {
        return Optional.ofNullable(
                groups.find {StoredGroup g -> g.id == id }
        )
    }

}
