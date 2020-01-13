package com.serversquad.polytech.mailapp.mailapp.model.mail.participant

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode
class Member {

    String id

    Member(String id) {
        this.id = id
    }

    void writeXml(MarkupBuilder xml) {
        xml.member(id: id)
    }
}
