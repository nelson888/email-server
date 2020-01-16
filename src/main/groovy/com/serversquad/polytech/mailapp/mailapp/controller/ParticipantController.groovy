package com.serversquad.polytech.mailapp.mailapp.controller

import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.Participant
import com.serversquad.polytech.mailapp.mailapp.repository.participant.ParticipantRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/participants")
@Api(value = "Controller to manipulates participants")
class ParticipantController {
    private final ParticipantRepository participantRepository;

    ParticipantController(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Get a participant by given id")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully retrieved the participant", response = Participant.class),
            @ApiResponse(code = 404, message = "participant not found")
    ])
    ResponseEntity getById(@PathVariable("id") String id) {
        return ResponseEntity.of(participantRepository.getById(id))
    }

    @GetMapping("/byName/{name}")
    @ApiOperation(value = "Get a participant by given name")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully retrieved the participant", response = Participant.class),
            @ApiResponse(code = 404, message = "participant not found")
    ])
    ResponseEntity getByName(@PathVariable("name") String name) {
        return ResponseEntity.of(participantRepository.getByName(name))
    }
    @GetMapping("/all")
    @ApiOperation(value = "Get all participants")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully retrieved participants", response = List.class),
    ])
    ResponseEntity getByAll() {
        return ResponseEntity.ok(participantRepository.getAll())
    }


}
