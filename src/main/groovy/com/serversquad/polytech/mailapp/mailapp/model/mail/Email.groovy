package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.fasterxml.jackson.annotation.JsonFormat
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Historic
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode
class Email<G> {
    String uuid
    String object
    // json format for FrontEmail
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM dd yyyy HH:mm:ss 'GMT'Z", locale = 'en_US')
    Date creationDate // JS Date
    List<Participant> participants
    List<G> groups
    Historic historic
}
