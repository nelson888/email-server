package com.serversquad.polytech.mailapp.mailapp.model.mail.participant

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode(includes = ["id"])
class Group<M> {

    String id
    String name
    Boolean canWrite
    List<M> members

}
