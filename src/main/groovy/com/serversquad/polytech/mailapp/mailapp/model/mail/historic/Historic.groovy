package com.serversquad.polytech.mailapp.mailapp.model.mail.historic

import groovy.transform.EqualsAndHashCode
import groovy.xml.MarkupBuilder;

@EqualsAndHashCode
public class Historic {

  List<Message> messages;

  void writeXml(MarkupBuilder xml) {
    xml.historic {
      messages.each {m -> m.writeXml(xml)}
    }
  }
}
