package com.serversquad.polytech.mailapp.mailapp.model.mail.historic

import com.serversquad.polytech.mailapp.mailapp.model.converter.XSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.AttachmentRef
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder;

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode
public class Message {

  String emitter;
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
