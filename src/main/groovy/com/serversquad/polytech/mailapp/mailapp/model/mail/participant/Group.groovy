package com.serversquad.polytech.mailapp.mailapp.model.mail.participant

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode
class Group {
    String id
    String name
    Boolean canWrite
    List<Member> members

    void writeXml(MarkupBuilder xml) {
        xml.group(id: id, name: name, can_write: canWrite) {
            members.each { Member m -> m.writeXml(xml) }
        }
    }
}
