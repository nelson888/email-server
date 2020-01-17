package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.serversquad.polytech.mailapp.mailapp.model.converter.XSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Historic
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.message.FrontMessage
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.message.StoredMessage
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.FrontGroup
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.StoredGroup
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Member
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import com.serversquad.polytech.mailapp.mailapp.repository.bodyformat.BodySchemaRepository
import com.serversquad.polytech.mailapp.mailapp.repository.email.body.BodyRepository
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder

@ToString(includePackage = false, includeFields = true, includeNames = true, includeSuperFields = true)
@EqualsAndHashCode(callSuper = true)
class StoredEmail extends Email<StoredGroup> {

    void writeXml(MarkupBuilder xml) {
        xml.'xm-mail'(xmlns: 'polytech/app5/xm-mail/0.2.0', creation_moment: XSDateConverter.format(creationDate), uuid: uuid) {
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

    FrontEmail toFrontEmail(BodyRepository bodyRepository, BodySchemaRepository schemaRepository) {
        List<FrontGroup> groups = groups.collect { StoredGroup group ->
            FrontGroup frontGroup = new FrontGroup(id: group.id, name: group.name, canWrite: group.canWrite)
            frontGroup.members = group.members.collect { Member member ->
                return participants.find { Participant participant -> participant.id == member.id }
            }.toList()
            return frontGroup
        }.toList()
        Historic historic = new Historic(attachments: this.historic.attachments.collect())
        historic.messages = this.historic.messages.collect {
            StoredMessage message = (StoredMessage) it
            StoredBody body = bodyRepository.getById(this, message.bodyRef.id)
            body.format = schemaRepository.getByUrlWithoutContent(body.format).url
            body.content = getContentWithoutRootTag(body.content)
            return new FrontMessage(
                    emitter: message.emitter,
                    emissionMoment: new Date(message.emissionMoment.time),
                    attachments: message.attachments.collect(),
                    body: body)
        }

        return new FrontEmail(uuid: uuid, object: object, creationDate: new Date(creationDate.time),
                participants: participants.collect(),
                groups: groups, historic: historic)
    }

    static String getContentWithoutRootTag(String xml) {
        int firstIndex = xml.indexOf(">")
        return xml.substring(xml.indexOf(">", firstIndex + 1) + 1, xml.lastIndexOf('</body>'))
    }
}
