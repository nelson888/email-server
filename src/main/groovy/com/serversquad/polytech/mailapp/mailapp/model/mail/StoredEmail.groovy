package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.serversquad.polytech.mailapp.mailapp.model.converter.XSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Historic
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.FrontGroup
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Group
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Member
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder;

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode
public class StoredEmail  {

  String uuid;
  String object;
  Date creationDate;
  List<Participant> participants;
  List<Group> groups
  Historic historic;

  void writeXml(MarkupBuilder xml) {
    xml.email(creation_moment: XSDateConverter.format(creationDate), uuid: uuid) {
      object(object)
      participants() {
        participants.each { Participant p -> p.writeXml(xml)}
      }
      groups() {
        groups.each {Group g -> g.writeXml(xml)}
      }
      historic.writeXml(xml)
    }
  }

  FrontEmail toFrontEmail() {
    List<FrontGroup> groups = groups.collect { Group group ->
      FrontGroup frontGroup = new FrontGroup(id: group.id, name: group.name, canWrite: group.canWrite)
      frontGroup.members = group.members.collect { Member member ->
        return participants.find { Participant participant -> participant.id == member.id }
      }.toList()
      return frontGroup
    }.toList()
    return new FrontEmail(uuid: uuid, object: object, creationDate: creationDate,
            participants: participants,
            groups: groups, historic: historic)
  }

}
