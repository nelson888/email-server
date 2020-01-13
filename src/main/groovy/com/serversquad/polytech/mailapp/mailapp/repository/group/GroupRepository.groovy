package com.serversquad.polytech.mailapp.mailapp.repository.group

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.StoredGroup
import com.serversquad.polytech.mailapp.mailapp.repository.email.EmailRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class GroupRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupRepository.class)

    private List<StoredGroup> groups

    GroupRepository(EmailRepository emailRepository) {
        groups = emailRepository.getAll()
        .collectMany { StoredEmail email -> email.groups }
        .unique()
        .toList()
        LOGGER.info("Groups loaded")
        LOGGER.info(groups.toString())
    }

    List<StoredGroup> getAll() {
        return groups
    }

    Optional<StoredGroup> getById(String id) {
        return Optional.ofNullable(
                groups.find {StoredGroup g -> g.id == id }
        )
    }

}
