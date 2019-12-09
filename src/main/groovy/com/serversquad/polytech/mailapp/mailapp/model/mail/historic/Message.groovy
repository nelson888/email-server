package com.serversquad.polytech.mailapp.mailapp.model.mail.historic

import com.serversquad.polytech.mailapp.mailapp.model.converter.JSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.AttachmentRef
import groovy.xml.MarkupBuilder;

public class Message {

  String emitter;
  Date emissionMoment;
  List<AttachmentRef> attachments
  String body

  void writeXml(MarkupBuilder xml) {
    xml.message([emitter: emitter, emission_moment: emissionMoment ? JSDateConverter.format(emissionMoment) : null]) {
      xml.body(body)
      xml.attachments() {
        attachments.each {a -> a.writeXml(xml)}
      }
    }
  }
}
