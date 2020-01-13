package com.serversquad.polytech.mailapp.mailapp.controller

import com.serversquad.polytech.mailapp.mailapp.repository.group.GroupRepository
import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/groups")
@Api(value = "Controller to manipulates groups")
class GroupController {

    private final GroupRepository grouprepository;

    GroupController(GroupRepository grouprepository) {
        this.grouprepository = grouprepository;
    }
    @PostMapping("/{id}")
    ResponseEntity getById(@PathVariable("id") String id) {
        return ResponseEntity.of(grouprepository.getById(id))
    }

    @PostMapping("/all")
    ResponseEntity getByAll() {
        return ResponseEntity.ok(grouprepository.getAll())
    }
}
