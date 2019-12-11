package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.FrontGroup
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * This is the email representation for the front end
 */
@ToString(includePackage = false, includeFields = true, includeNames = true, includeSuperFields = true)
@EqualsAndHashCode(callSuper = true)
class FrontEmail extends Email<FrontGroup> {

    StoredEmail toStoredEmail() {
        //TODO convert this email to a stored email (get inspiration from StoredEmail.toFrontEmail()
        null
    }
}
