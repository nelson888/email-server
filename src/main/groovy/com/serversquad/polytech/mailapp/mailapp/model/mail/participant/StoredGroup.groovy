package com.serversquad.polytech.mailapp.mailapp.model.mail.participant

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder

/**
 * This is the email representation that will be stored
 */
@ToString(includePackage = false, includeFields = true, includeNames = true, includeSuperFields = true)
@EqualsAndHashCode(callSuper = true)
class StoredGroup extends Group<Member> {

    void writeXml(MarkupBuilder xml) {
        xml.group(id: id, name: name, can_write: canWrite) {
            members.each { Member m -> m.writeXml(xml) }
        }
    }
}
