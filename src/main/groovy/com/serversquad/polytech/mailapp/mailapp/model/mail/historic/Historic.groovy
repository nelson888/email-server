package com.serversquad.polytech.mailapp.mailapp.model.mail.historic

import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.Attachment
import groovy.transform.EqualsAndHashCode
import groovy.xml.MarkupBuilder;

@EqualsAndHashCode
public class Historic {

  List<Message> messages;
  List<Attachment> attachments;

  void writeXml(MarkupBuilder xml) {
    xml.historic {
      messages.each {m -> m.writeXml(xml)}
    }
  }
}
