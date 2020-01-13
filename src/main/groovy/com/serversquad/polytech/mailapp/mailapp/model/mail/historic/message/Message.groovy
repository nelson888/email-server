package com.serversquad.polytech.mailapp.mailapp.model.mail.historic.message

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.serversquad.polytech.mailapp.mailapp.model.converter.XSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.BodyRef
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredBody
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.AttachmentRef
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode
class Message {

    String emitter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM dd yyyy HH:mm:ss 'GMT'Z", locale = 'en_US')
    Date emissionMoment
    List<AttachmentRef> attachments

}
