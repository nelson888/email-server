package com.serversquad.polytech.mailapp.mailapp.controller

import com.serversquad.polytech.mailapp.mailapp.model.mail.FrontEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.repository.email.EmailRepository
import com.serversquad.polytech.mailapp.mailapp.service.EmailSender
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/emails")
@Api(value = "Controller to manipulates emails")
class EmailController {

    private final EmailRepository emailRepository
    private final EmailSender emailSender

    EmailController(EmailRepository emailRepository, EmailSender emailSender) {
        this.emailRepository = emailRepository
        this.emailSender = emailSender
    }

    @PostMapping
    @ApiOperation(value = "Save the given email into the server")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully saved the email")
    ])
    ResponseEntity saveEmail(@RequestBody FrontEmail email) throws IOException {
        emailRepository.saveEmail(email.toStoredEmail())
        return ResponseEntity.ok().build()
    }

    /**
     * Get the mail with the given id
     * @param id the id of the email
     * @return
     */
    @PostMapping("/{id}")
    ResponseEntity getById(@PathVariable("id") int id) {
        return ResponseEntity.of(emailRepository.getById(id).map({ StoredEmail e -> e.toFrontEmail() }))
    }

}
