package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Historic
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.FrontGroup
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Group
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Member
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

  FrontEmail toFrontEmail() {
    List<FrontGroup> groups = groups.collect { Group group ->
      FrontGroup frontGroup = new FrontGroup(id: group.id, name: group.name, canWrite: group.canWrite)
      frontGroup.members = group.members.collect { Member member ->
        return participants.find { SimpleParticipant participant -> participant.id == member.id }
      }.toList()
      return frontGroup
    }.toList()
    return new FrontEmail(uuid: uuid, object: object, creationDate: creationDate,
            participants: participants,
            groups: groups, historic: historic)
  }

}
