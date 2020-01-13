package com.serversquad.polytech.mailapp.mailapp.controller

import com.serversquad.polytech.mailapp.mailapp.repository.participant.ParticipantRepository
import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
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
    @PostMapping("/{id}")
    ResponseEntity getById(@PathVariable("id") String id) {
        return ResponseEntity.of(participantRepository.getById(id))
    }

    @PostMapping("/byName/{name}")
    ResponseEntity getByName(@PathVariable("name") String name) {
        return ResponseEntity.of(participantRepository.getByName(name))
    }
    @PostMapping("/all")
    ResponseEntity getByAll() {
        return ResponseEntity.ok(participantRepository.getAll())
    }


}
