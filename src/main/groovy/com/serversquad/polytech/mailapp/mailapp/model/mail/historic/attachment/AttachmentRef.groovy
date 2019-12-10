package com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode
class AttachmentRef {
    String id
    String version

    void writeXml(MarkupBuilder xml) {
        xml.attachment_reference(id: id, version: version)
    }
}
