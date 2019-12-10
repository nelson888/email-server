package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Historic
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.FrontGroup
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

// TODO create super class for fields in common with StoredEmail
@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode
class FrontEmail {
    String uuid
    String object
    Date creationDate // JS Date
    List<Participant> participants
    List<FrontGroup> groups
    Historic historic

}
