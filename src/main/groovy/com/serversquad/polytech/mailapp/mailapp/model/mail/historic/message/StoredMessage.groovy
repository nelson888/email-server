package com.serversquad.polytech.mailapp.mailapp.model.mail.historic.message

import com.serversquad.polytech.mailapp.mailapp.model.converter.XSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.BodyRef
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode(callSuper = true)
class StoredMessage extends Message {

    BodyRef bodyRef // for back email

    void writeXml(MarkupBuilder xml) {
        xml.message(emitter: emitter, emission_moment: XSDateConverter.format(emissionMoment)) {
            bodyRef.writeXml(xml)
            xml.attachments() {
                attachments.each { a -> a.writeXml(xml) }
            }
        }
    }
}
