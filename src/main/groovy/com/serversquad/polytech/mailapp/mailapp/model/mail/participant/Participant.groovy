package com.serversquad.polytech.mailapp.mailapp.model.mail.participant

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode(includes = ["id"])
class Participant {

    String id
    String name

    void writeXml(MarkupBuilder xml) {
        xml.participant(id: id, name: name)
    }
}
