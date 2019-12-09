package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Historic
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import groovy.transform.EqualsAndHashCode;

@EqualsAndHashCode
public class StoredEmail  {

  Integer id;
  String object;
  Date creationDate;
  List<Participant> participants;
  Historic historic;




}
