package com.serversquad.polytech.mailapp.mailapp.excepetion

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(value = HttpStatus.NOT_FOUND)
class NotFoundException extends RuntimeException {
    NotFoundException(String message) {
        super(message)
    }
}
