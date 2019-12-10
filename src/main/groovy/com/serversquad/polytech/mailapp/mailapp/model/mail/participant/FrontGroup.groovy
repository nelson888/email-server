package com.serversquad.polytech.mailapp.mailapp.model.mail.participant

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

// TODO create super class for frontgroup et group
@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode
class FrontGroup {
    String id
    String name
    Boolean canWrite
    List<Participant> members
}
