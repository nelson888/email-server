package com.serversquad.polytech.mailapp.mailapp.controller

import com.serversquad.polytech.mailapp.mailapp.excepetion.NotFoundException
import com.serversquad.polytech.mailapp.mailapp.model.mail.BodyRef
import com.serversquad.polytech.mailapp.mailapp.model.mail.BodySchema
import com.serversquad.polytech.mailapp.mailapp.model.mail.FrontEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredBody
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Historic
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.message.StoredMessage
import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import com.serversquad.polytech.mailapp.mailapp.model.request.SaveMailRequest
import com.serversquad.polytech.mailapp.mailapp.repository.bodyformat.BodySchemaRepository
import com.serversquad.polytech.mailapp.mailapp.repository.email.EmailRepository
import com.serversquad.polytech.mailapp.mailapp.repository.email.body.BodyRepository
import com.serversquad.polytech.mailapp.mailapp.repository.group.GroupRepository
import com.serversquad.polytech.mailapp.mailapp.repository.participant.ParticipantRepository

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/emails")
@Api(value = "Controller to manipulates emails")
class EmailController {

    private final EmailRepository emailRepository
    private final ParticipantRepository participantRepository
    private final BodyRepository bodyRepository
    private final GroupRepository groupRepository
    private final BodySchemaRepository bodySchemaRepository

    EmailController(EmailRepository emailRepository,
                    ParticipantRepository participantRepository, GroupRepository groupRepository,
                    BodyRepository bodyRepository, BodySchemaRepository bodySchemaRepository) {
        this.emailRepository = emailRepository
        this.participantRepository = participantRepository
        this.groupRepository = groupRepository
        this.bodyRepository = bodyRepository
        this.bodySchemaRepository = bodySchemaRepository
    }

    @PostMapping
    @ApiOperation(value = "Save the given email into the server")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully saved the email", response = FrontEmail.class)
    ])
    ResponseEntity saveEmail(@RequestBody SaveMailRequest request) throws IOException {
        if ([request.emitter, request.body, request.bodySchema].any(Objects.&isNull)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Some fields are missing")
        }
        if (!request.participants.contains(request.emitter)) {
            request.participants.add(request.emitter)
        }
        BodySchema schema = bodySchemaRepository.getByName(request.bodySchema)
                .orElseThrow({ new NotFoundException("schema ${request.bodySchema} doesn't exists")})
        schema.validate(request.body)
        StoredBody storedBody = bodyRepository.save(request.uuid, request.body, schema.name)
        StoredMessage message = new StoredMessage(
                emitter: request.emitter,
                emissionMoment: new Date(),
                attachments: [],
                bodyRef: new BodyRef(id: storedBody.id, format: schema.url)
        )
        StoredEmail mail
        if (!request.uuid) {
            mail = new StoredEmail(
                    uuid: "xmmail_${UUID.randomUUID().toString()}",
                    object: request.object,
                    creationDate: new Date(),
                    participants: request.participants.collect { participantRepository.getById(it).orElse(null) }
                            .findAll(Objects.&nonNull).toList(),
                    groups: request.groups.collect { groupRepository.getByName(it).orElse(null) }
                            .findAll(Objects.&nonNull).toList(),
                    historic: new Historic(
                            messages: [],
                            attachments: []
                    )
            )
        } else {
            mail = emailRepository.getByUUID(request.uuid)
                    .orElseThrow({ new NotFoundException("Email with id ${request.uuid} doesn't exists") })
            List<Participant> participants = request.participants
                    .collect { participantRepository.getById(it).orElse(null) }
                    .findAll(Objects.&nonNull)
                    .toList()
            participants.addAll(mail.participants)
            mail.participants = participants.unique()

        }
        mail.historic.messages.add(0, message)
        mail = emailRepository.saveEmail(mail)
        return ResponseEntity.ok(mail.toFrontEmail(bodyRepository, bodySchemaRepository))
    }

    /**
     * Get the mail with the given id
     * @param id the id of the email
     * @return
     */
    @PostMapping("/{uuid}")
    @ApiOperation(value = "Get an email by given UUID")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully retrieved the email", response = FrontEmail.class),
            @ApiResponse(code = 404, message = "Email not found")
    ])
    ResponseEntity getById(@PathVariable("uuid") String uuid) {
        return ResponseEntity.of(emailRepository.getByUUID(uuid).map({ StoredEmail e -> e.toFrontEmail(bodyRepository, bodySchemaRepository) }))
    }

    @PostMapping("/byExpeditor/{expeditor}")
    @ApiOperation(value = "Get all emails by given expeditor")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully retrieved emails", response = List.class),
    ])
    ResponseEntity getByExpeditor(@PathVariable("expeditor") String expeditor) {
        return ResponseEntity.ok(emailRepository.getAllByExpeditor(expeditor).collect({ StoredEmail e -> e.toFrontEmail(bodyRepository, bodySchemaRepository) }))
    }
    @PostMapping("/byParticipant/{participant}")
    @ApiOperation(value = "Get all emails by given expeditor")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully retrieved emails", response = List.class),
    ])
    ResponseEntity getByParticipant(@PathVariable("participant") String participant) {
        return ResponseEntity.ok(emailRepository.getAllByParticipantId(participant).collect({ StoredEmail e -> e.toFrontEmail(bodyRepository, bodySchemaRepository) }))
    }

    @PostMapping("/byParticipantName/{name}")
    @ApiOperation(value = "Get all emails by given name")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully retrieved emails", response = List.class),
    ])
    ResponseEntity getByParticipantName(@PathVariable("name") String name) {
        return ResponseEntity.ok(emailRepository.getAllByParticipantName(name).collect({ StoredEmail e -> e.toFrontEmail(bodyRepository, bodySchemaRepository) }))
    }

    /**
     * Get the mail with the given id
     * @param id the id of the email
     * @return
     */
    @PostMapping("/all")
    @ApiOperation(value = "Get all emails")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully retrieved emails", response = List.class),
    ])
    ResponseEntity getByAll() {
        return ResponseEntity.ok(emailRepository.getAll().collect { StoredEmail e -> e.toFrontEmail(bodyRepository, bodySchemaRepository) })
    }

}
