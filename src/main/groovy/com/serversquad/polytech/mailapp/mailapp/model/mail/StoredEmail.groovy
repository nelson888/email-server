package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Historic
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Group
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.SimpleParticipant
import groovy.transform.EqualsAndHashCode;

@EqualsAndHashCode
public class StoredEmail  {

  String uuid;
  String object;
  Date creationDate;
  List<SimpleParticipant> participants;
  List<Group> groups
  Historic historic;




}
