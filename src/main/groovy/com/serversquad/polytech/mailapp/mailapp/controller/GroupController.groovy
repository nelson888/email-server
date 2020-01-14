package com.serversquad.polytech.mailapp.mailapp.controller

import com.serversquad.polytech.mailapp.mailapp.model.mail.participant.FrontGroup
import com.serversquad.polytech.mailapp.mailapp.repository.group.GroupRepository
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
@RequestMapping("/groups")
@Api(value = "Controller to manipulates groups")
class GroupController {

    private final GroupRepository groupRepository;

    GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
    @PostMapping("/{id}")
    @ApiOperation(value = "Get a group by given id")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully retrieved the group", response = FrontGroup.class),
            @ApiResponse(code = 404, message = "group not found")
    ])
    ResponseEntity getById(@PathVariable("id") String id) {
        return ResponseEntity.of(groupRepository.getById(id))
    }

    @PostMapping("/all")
    @ApiOperation(value = "Get all groups")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successfully retrieved groups", response = List.class),
    ])
    ResponseEntity getByAll() {
        return ResponseEntity.ok(groupRepository.getAll())
    }
}
