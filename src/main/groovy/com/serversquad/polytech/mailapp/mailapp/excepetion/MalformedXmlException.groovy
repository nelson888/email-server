package com.serversquad.polytech.mailapp.mailapp.excepetion

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class MalformedXmlException extends RuntimeException {
    MalformedXmlException(Throwable throwable) {
        super(throwable)
    }
}
