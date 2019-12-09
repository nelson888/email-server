package com.serversquad.polytech.mailapp.mailapp.model.mail.participant

import groovy.xml.MarkupBuilder;

public class SimpleParticipant extends Participant {

  String id
  String name

  @Override
  boolean isSimple() {
    return true
  }

  @Override
  boolean isGroup() {
    return false
  }

  @Override
  void writeXml(MarkupBuilder builder) {
    builder.participant(type: type.toString().toLowerCase(), priority: priority, email)
  }
}
