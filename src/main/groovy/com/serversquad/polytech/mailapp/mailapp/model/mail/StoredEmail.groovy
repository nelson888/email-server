package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.serversquad.polytech.mailapp.mailapp.model.converter.XSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.FrontGroup
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.StoredGroup
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Member
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder

@ToString(includePackage = false, includeFields = true, includeNames = true, includeSuperFields = true)
@EqualsAndHashCode(callSuper = true)
class StoredEmail extends Email<StoredGroup> {

    void writeXml(MarkupBuilder xml) {
        xml.email(creation_moment: XSDateConverter.format(creationDate), uuid: uuid) {
            object(object)
            participants() {
                participants.each { Participant p -> p.writeXml(xml) }
            }
            groups() {
                groups.each { StoredGroup g -> g.writeXml(xml) }
            }
            historic.writeXml(xml)
        }
    }

    FrontEmail toFrontEmail() {
        List<FrontGroup> groups = groups.collect { StoredGroup group ->
            FrontGroup frontGroup = new FrontGroup(id: group.id, name: group.name, canWrite: group.canWrite)
            frontGroup.members = group.members.collect { Member member ->
                return participants.find { Participant participant -> participant.id == member.id }
            }.toList()
            return frontGroup
        }.toList()
        return new FrontEmail(uuid: uuid, object: object, creationDate: creationDate,
                participants: participants,
                groups: groups, historic: historic)
    }

}
