package com.serversquad.polytech.mailapp.mailapp.model.mail.historic.message

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredBody
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode(callSuper = true)
class FrontMessage extends Message {

    StoredBody body

}
