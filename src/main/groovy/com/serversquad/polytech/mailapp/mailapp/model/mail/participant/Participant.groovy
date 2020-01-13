package com.serversquad.polytech.mailapp.mailapp.model.mail.participant

import com.serversquad.polytech.mailapp.mailapp.model.mail.ParticipantType
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode(includes = ["id"])
class Participant {

    ParticipantType type //= ParticipantType.NORMAL;
    Integer priority //= 0
    String id
    String name

    void writeXml(MarkupBuilder xml) {
        xml.participant(type: type.toString().toLowerCase(), priority: priority, id: id, name: name)
    }
}
