package com.serversquad.polytech.mailapp.mailapp.controller

import com.serversquad.polytech.mailapp.mailapp.model.mail.BodySchema
import com.serversquad.polytech.mailapp.mailapp.repository.bodyformat.BodySchemaRepository
import groovy.util.logging.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/schemas")
@Api(value = "Controller to handle body schemas")
@Slf4j('LOGGER')
class BodySchemaController {

    private final BodySchemaRepository bodySchemaRepository

    BodySchemaController(BodySchemaRepository bodySchemaRepository) {
        this.bodySchemaRepository = bodySchemaRepository
    }

    @PostMapping("/{name}")
    @ApiOperation(value = "Get a participant by given id")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully retrieved the schema", response = BodySchema.class),
            @ApiResponse(code = 404, message = "participant not found")
    ])
    ResponseEntity getByName(@PathVariable("name") String name) {
        return ResponseEntity.of(bodySchemaRepository.getByName(name))
    }

    @PostMapping("/all")
    @ApiOperation(value = "Get all emails")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully retrieved schemas", response = List.class),
    ])
    ResponseEntity getAll() {
        return ResponseEntity.ok(bodySchemaRepository.getAll())
    }

}
