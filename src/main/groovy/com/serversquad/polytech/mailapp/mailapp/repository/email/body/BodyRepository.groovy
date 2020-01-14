package com.serversquad.polytech.mailapp.mailapp.repository.email.body

import com.serversquad.polytech.mailapp.mailapp.model.mail.Email
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredBody

interface BodyRepository {

    StoredBody getById(Email email, String id)
    StoredBody save(String mailUuid, String content, String format)

}
