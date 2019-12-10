package com.serversquad.polytech.mailapp.mailapp.model.mail.historic

import com.fasterxml.jackson.annotation.JsonFormat
import com.serversquad.polytech.mailapp.mailapp.model.converter.XSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.AttachmentRef
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder;

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode
public class Message {

  String emitter;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM dd yyyy HH:mm:ss 'GMT'Z", locale = 'en_US')
  Date emissionMoment;
  List<AttachmentRef> attachments
  String body

  void writeXml(MarkupBuilder xml) {
    xml.message(emitter: emitter, emission_moment: XSDateConverter.format(emissionMoment)) {
      xml.body(body)
      xml.attachments() {
        attachments.each {a -> a.writeXml(xml)}
      }
    }
  }
}
