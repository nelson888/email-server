package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Historic
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.FrontGroup
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Group
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.SimpleParticipant

// TODO create super class for fields in common with StoredEmail
class FrontEmail {
    String uuid
    String object
    Date creationDate // JS Date
    List<SimpleParticipant> participants
    List<FrontGroup> groups
    Historic historic

}
