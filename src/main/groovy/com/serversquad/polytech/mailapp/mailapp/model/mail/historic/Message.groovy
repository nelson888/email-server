package com.serversquad.polytech.mailapp.mailapp.model.mail.historic

import com.serversquad.polytech.mailapp.mailapp.model.converter.JSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.Attachment
import com.serversquad.polytech.mailapp.mailapp.model.mail.reference.AttachmentRef
import groovy.xml.MarkupBuilder;

public class Message {

  String emitter;
  Date emission_moment;
  List<AttachmentRef> attachments
  String body

  void writeXml(MarkupBuilder xml) {
    xml.message([emitter: emitter, creation_date: emission_moment ? JSDateConverter.format(emission_moment) : null]) {
      xml.body(body)
      xml.attachments() {
        attachments.each {a -> a.writeXml(xml)}
      }
    }
  }
}
