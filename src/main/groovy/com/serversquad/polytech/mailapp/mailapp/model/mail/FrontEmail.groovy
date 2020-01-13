package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.FrontGroup
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Member
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.StoredGroup
import com.serversquad.polytech.mailapp.mailapp.repository.participant.ParticipantRepository
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * This is the email representation for the front end
 */
@ToString(includePackage = false, includeFields = true, includeNames = true, includeSuperFields = true)
@EqualsAndHashCode(callSuper = true)
class FrontEmail extends Email<FrontGroup> {

    StoredEmail toStoredEmail() {
        List<StoredGroup> groups = groups.collect { FrontGroup group ->
            StoredGroup storedGroup = new StoredGroup(id: group.id, name: group.name, canWrite: group.canWrite)
            for (Participant participant : group.members) {
                storedGroup.members.add(new Member(id: participant.id));
            }
            return storedGroup
        }.toList()
        return new StoredEmail(uuid: uuid, object: object, creationDate: creationDate,
                participants: participants,
                groups: groups, historic: historic)
    }

}
