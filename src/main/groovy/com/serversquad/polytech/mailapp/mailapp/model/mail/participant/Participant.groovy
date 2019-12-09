package com.serversquad.polytech.mailapp.mailapp.model.mail.participant

import com.serversquad.polytech.mailapp.mailapp.model.mail.ParticipantType
import groovy.transform.EqualsAndHashCode
import groovy.xml.MarkupBuilder

@EqualsAndHashCode
abstract class Participant {

    ParticipantType type = ParticipantType.NORMAL;
    Integer priority = 0

    abstract boolean isSimple()
    abstract boolean isGroup()
    abstract void writeXml(MarkupBuilder builder)

    GroupParticipant asGroup() {
        return GroupParticipant.cast(this)
    }

    SimpleParticipant asSimple() {
        return SimpleParticipant.cast(this)
    }


}
