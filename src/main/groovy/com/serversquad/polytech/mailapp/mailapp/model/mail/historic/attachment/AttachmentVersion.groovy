package com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment

import com.fasterxml.jackson.annotation.JsonFormat
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode
class AttachmentVersion {

    Integer number
    Integer size
    String creator
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM dd yyyy HH:mm:ss 'GMT'Z", locale = 'en_US')
    String insertionMoment
    String content //base64

    void writeXml(MarkupBuilder xml) {
        xml.version(number: number, size: size, creator: creator, insertion_moment: insertionMoment, content)
    }
}
