package com.serversquad.polytech.mailapp.mailapp.service


import com.serversquad.polytech.mailapp.mailapp.model.converter.JSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.ParticipantType;
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Historic
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Message
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.Attachment
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Group
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.GroupParticipant
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Member
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.SimpleParticipant
import com.serversquad.polytech.mailapp.mailapp.model.mail.reference.AttachmentRef
import groovy.util.slurpersupport.GPathResult
import groovy.xml.XmlUtil;
import org.springframework.stereotype.Component;

import groovy.xml.MarkupBuilder

// for mixed content see https://kodejava.org/how-do-i-get-mixed-content-of-an-xml-element/
@Component
public class EmailParser {

    public StoredEmail parseEmail(byte[] bytes) {
        return parseEmail(new String(bytes))
    }

    public StoredEmail parseEmail(String s) {
        def root = new XmlSlurper().parseText(s)

        def email = new StoredEmail()
        email.uuid = root['@uuid'] // generate id?
        email.object = getRawText(root.object)
        email.creationDate = JSDateConverter.parse(root['@creation_moment'])
        email.participants = parseParticipants(root.participants)
        email.groups = parseGroups(root.groups)
        email.historic = parseHistoric(root.historic)
        return email
    }

    String toString(StoredEmail email) throws IOException {
        StringWriter writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.omitNullAttributes = true
        xml.omitEmptyAttributes = true
        xml.expandEmptyElements = true
        xml.email {
            object(email.object)
            creationDate(JSDateConverter.format(email.creationDate))
            participants() {
                email.participants.each( { Participant p -> p.writeXml(xml)})
            }
            email.historic.writeXml(xml)
        }
        return writer.toString()
                .replaceAll('&lt;', '<')// TODO change find something else
                .replaceAll('&gt;', '>')
    }

    private static String getRawText(GPathResult node) {
        // TODO remove root tag
        String nodeName = node.name()
        String raw = XmlUtil.serialize(node).replace('<?xml version="1.0" encoding="UTF-8"?>' + "<$nodeName>", '')
        return raw
                .replaceFirst("</$nodeName>", '').trim() // TODO replace last
    }

    private static List<SimpleParticipant> parseParticipants(GPathResult node) {
        def participants = []
        node.children().each { GPathResult child ->
            participants.add(parseSimpleParticipant(child))
        }
        return participants
    }

    private static List<Group> parseGroups(GPathResult node) {
        def groups = []
        node.children().each { GPathResult child ->
            groups.add(parseSimpleGroup(child))
        }
        return groups
    }

    private static Group parseSimpleGroup(GPathResult child) {
        return new Group(id: child['@id'], name: child['@name'], canWrite: child['@can_write']=='true',
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



    private static Participant parseSimpleParticipant(GPathResult child) {
        return new SimpleParticipant(id: child['@id'], name: child['@name'],
                type: parseType(child), priority: child['@priority']?.toInteger() ?: 0)
    }


    private static ParticipantType parseType(GPathResult child) {
        ParticipantType type = ParticipantType.NORMAL
        String typeAttr = child['@type']
        if (typeAttr) {
            try {
                type = ParticipantType.valueOf(typeAttr.toUpperCase())
            } catch (IllegalArgumentException ignored) {}
        }
        return type
    }

    private static Participant parseGroupParticipant(GPathResult child) {
        return new GroupParticipant(name: child['@name'], type: parseType(child),
                priority: child['@priority']?.toInteger() ?: 0,
                participants: parseParticipants(child))
    }

    private static Historic parseHistoric(GPathResult node) {
        Historic historic = new Historic(messages: [])
        node.children().each { GPathResult child ->
            if (child.name() == "message") {
                historic.messages.add(parseMessage(child))
            }
        }
        return historic
    }

    private static Message parseMessage(GPathResult node) {
        List<AttachmentRef> attachments = []
        node.attachments.children().each { GPathResult child ->
            if (child.name() == "attachment") {
                attachments.add(parseAttachmentRef(child))
            }
        }
        return new Message(emitter: node['@emitter'],
                emission_moment: node['@emission_moment'].toString() ? JSDateConverter.parse(node['@emission_moment']) : null,
                attachments: attachments,
                body: getRawText(node.body))
    }

    private static Attachment parseAttachment(GPathResult node) {
        new Attachment(
                id: node['@id']?.toInteger() ?: 0,
                name: node['@name'],
                size: node['@size']?.toInteger() ?: 0,
                version: node['@version'],
                expirationDate: node['@creation_date'].toString() ? JSDateConverter.parse(node['@expirationDate']) : null,
                data: node.text().bytes
        )
    }

    private static AttachmentRef parseAttachmentRef(GPathResult node) {
        return new AttachmentRef(id: node['@id'], version: node['@version'])
    }
}