package com.serversquad.polytech.mailapp.mailapp.repository.email.body

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredBody

interface BodyRepository {

    StoredBody getById(String id)
    StoredBody save(String content, String format)

}
