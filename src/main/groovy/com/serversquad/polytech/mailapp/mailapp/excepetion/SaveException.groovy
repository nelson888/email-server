package com.serversquad.polytech.mailapp.mailapp.excepetion

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
class SaveException extends RuntimeException {
    SaveException(String message) {
        super(message)
    }
}
