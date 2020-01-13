package com.serversquad.polytech.mailapp.mailapp.controller

import com.serversquad.polytech.mailapp.mailapp.excepetion.NotFoundException
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Message
import com.serversquad.polytech.mailapp.mailapp.model.request.SaveMailRequest
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
    ResponseEntity saveEmail(@RequestBody SaveMailRequest request) throws IOException {
        StoredEmail mail = emailRepository.getByUUID(request.uuid)
        .orElseThrow({ new NotFoundException("Email with id ${request.uuid} doesn't exists") })
        Message message = new Message(
                emitter: request.emitter,
                emissionMoment: new Date(),
                attachments: [],
                body: request.body
        )
        mail.historic.messages.add(message)
        emailRepository.saveEmail(mail)
        return ResponseEntity.ok(mail.toFrontEmail())
    }

    /**
     * Get the mail with the given id
     * @param id the id of the email
     * @return
     */
    @PostMapping("/{uuid}")
    ResponseEntity getById(@PathVariable("uuid") String uuid) {
        return ResponseEntity.of(emailRepository.getByUUID(uuid).map({ StoredEmail e -> e.toFrontEmail() }))
    }

    @PostMapping("/byExpeditor/{expeditor}")
    ResponseEntity getByExpeditor(@PathVariable("expeditor") String expeditor) {
        return ResponseEntity.ok(emailRepository.getAllByExpeditor(expeditor).collect({ StoredEmail e -> e.toFrontEmail() }))
    }
    @PostMapping("/byParticipant/{participant}")
    ResponseEntity getByParticipant(@PathVariable("participant") String participant) {
        return ResponseEntity.ok(emailRepository.getAllByParticipantId(participant).collect({ StoredEmail e -> e.toFrontEmail() }))
    }

    @PostMapping("/byParticipantName/{name}")
    ResponseEntity getByParticipantName(@PathVariable("name") String name) {
        return ResponseEntity.ok(emailRepository.getAllByParticipantName(name).collect({ StoredEmail e -> e.toFrontEmail() }))
    }

    /**
     * Get the mail with the given id
     * @param id the id of the email
     * @return
     */
    @PostMapping("/all")
    ResponseEntity getByAll() {
        return ResponseEntity.ok(emailRepository.getAll())
    }

}
