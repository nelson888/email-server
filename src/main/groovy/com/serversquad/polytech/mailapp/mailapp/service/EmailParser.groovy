package com.serversquad.polytech.mailapp.mailapp.service

import com.serversquad.polytech.mailapp.mailapp.model.converter.XSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.BodyRef
import com.serversquad.polytech.mailapp.mailapp.model.mail.ParticipantType
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Historic
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.message.StoredMessage
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.Attachment
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.AttachmentRef
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.AttachmentVersion
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.StoredGroup
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Member
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import groovy.util.slurpersupport.GPathResult
import groovy.xml.MarkupBuilder
import org.springframework.stereotype.Component

// for mixed content see https://kodejava.org/how-do-i-get-mixed-content-of-an-xml-element/
@Component
class EmailParser {

    StoredEmail parseEmail(byte[] bytes) {
        return parseEmail(new String(bytes))
    }

    StoredEmail parseEmail(String s) {
        def root = new XmlSlurper().parseText(s)

        def email = new StoredEmail()
        email.uuid = root['@uuid'] // generate id?
        email.object = getRawText(root.object)
        email.creationDate = XSDateConverter.parse(root['@creation_moment'])
        email.participants = parseParticipants(root.participants)
        email.groups = parseGroups(root.groups)
        email.historic = parseHistoric(root.historic)
        email.historic.messages.sort {
            - it.emissionMoment.time
        }
        return email
    }

    String toString(StoredEmail email) throws IOException {
        StringWriter writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.omitNullAttributes = true
        xml.omitEmptyAttributes = true
        xml.expandEmptyElements = true
        email.writeXml(xml)
        return writer.toString()
    }

    private static String getRawText(GPathResult node) {
        return node.text() // the body will be in another file
    }

    private static List<Participant> parseParticipants(GPathResult node) {
        return node.children().collect(EmailParser.&parseParticipant).toList()
    }

    private static List<StoredGroup> parseGroups(GPathResult node) {
        return node.children().collect(EmailParser.&parseGroup).toList()
    }

    private static StoredGroup parseGroup(GPathResult child) {
        return new StoredGroup(id: child['@id'], name: child['@name'], canWrite: child['@can_write'] == 'true',
                members: parseMembers(child))
    }

    private static List<Member> parseMembers(GPathResult node) {
        def members = []
        node.children().each { GPathResult child ->
            members.add(parseMember(child))
        }
        return members
    }

    private static Member parseMember(GPathResult child) {
        return new Member(id: child['@id'])
    }


    private static Participant parseParticipant(GPathResult child) {
        return new Participant(id: child['@id'], name: child['@name'],
                type: parseType(child), priority: child['@priority']?.toInteger() ?: 0)
    }


    private static ParticipantType parseType(GPathResult child) {
        ParticipantType type = ParticipantType.NORMAL
        String typeAttr = child['@type']
        if (typeAttr) {
            try {
                type = ParticipantType.valueOf(typeAttr.toUpperCase())
            } catch (IllegalArgumentException ignored) {
            }
        }
        return type
    }

    private static Historic parseHistoric(GPathResult node) {
        return new Historic(messages: parseMessages(node.messages), attachments: parseAttachments(node.attachments))
    }

    private static List<StoredMessage> parseMessages(GPathResult node) {
        return node.children().collect { GPathResult child ->
            new StoredMessage(emitter: child['@emitter'], emissionMoment: XSDateConverter.parse(child['@emission_moment']),
                    bodyRef: parseBodyRef(child['body']),
                    attachments: parseAttachmentRefs(child.attachments))
        }.toList()
    }

    private static BodyRef parseBodyRef(GPathResult node) {
        return new BodyRef(format: node['@format'], id: node['@id'])
    }

    private static List<Attachment> parseAttachments(GPathResult node) {
        return node.children().collect(EmailParser.&parseAttachment).toList()
    }

    private static List<AttachmentRef> parseAttachmentRefs(GPathResult node) {
        return node.children().collect(EmailParser.&parseAttachmentRef).toList()
    }

    private static Attachment parseAttachment(GPathResult node) {
        List<AttachmentVersion> versions = parseVersions(node)
        new Attachment(
                id: node['@id'],
                name: node['@name'],
                mimeType: node['@mimetype'],
                versions: versions
        )
    }

    private static AttachmentRef parseAttachmentRef(GPathResult node) {
        return new AttachmentRef(id: node['@id'], version: node['@version'])
    }

    private static List<AttachmentVersion> parseVersions(GPathResult node) {
        return node.children().collect { GPathResult child ->
            parseVersion(child)
        }.toList()
    }

    private static AttachmentVersion parseVersion(GPathResult node) {
        return new AttachmentVersion(
                number: node['@number']?.toInteger() ?: 0,
                size: node['@size']?.toInteger() ?: 0,
                creator: node['@creator'],
                content: node.toString(),
                insertionMoment: node['@insertion_moment'])
    }
}