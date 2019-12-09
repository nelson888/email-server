package com.serversquad.polytech.mailapp.mailapp.model.mail.historic

import com.serversquad.polytech.mailapp.mailapp.model.converter.JSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.Attachment
import groovy.xml.MarkupBuilder;

public class Message {

  String expeditor;
  Date creationDate;
  List<Attachment> attachments;
  String body

  void writeXml(MarkupBuilder xml) {
    xml.message([expeditor: expeditor, creation_date: creationDate ? JSDateConverter.format(creationDate) : null]) {
      xml.body(body)
      xml.attachments() {
        attachments.each {a -> a.writeXml(xml)}
      }
    }
  }
}
