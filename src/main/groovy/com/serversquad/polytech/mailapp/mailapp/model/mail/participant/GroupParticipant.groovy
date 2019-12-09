package com.serversquad.polytech.mailapp.mailapp.model.mail.participant

import groovy.xml.MarkupBuilder;

public class GroupParticipant extends Participant {

  String name;
  List<Participant> participants

  @Override
  boolean isGroup() {
    return true
  }

  @Override
  boolean isSimple() {
    return false
  }

  @Override
  void writeXml(MarkupBuilder xml) {
    xml.group(name: name, type: type.toString().toLowerCase(), priority: priority) {
      participants.each {p -> p.writeXml(xml) }
    }
  }
}
