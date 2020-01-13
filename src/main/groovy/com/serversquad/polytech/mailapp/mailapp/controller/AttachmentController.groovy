package com.serversquad.polytech.mailapp.mailapp.controller

import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.Attachment
import com.serversquad.polytech.mailapp.mailapp.storage.AttachmentStorage
import groovy.util.logging.Slf4j
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/attachment")
@Api(value = "Controller to handle attachments")
@Slf4j('LOGGER')
class AttachmentController {

    private final AttachmentStorage attachmentStorage

    AttachmentController(AttachmentStorage attachmentStorage) {
        this.attachmentStorage = attachmentStorage
    }

    @PostMapping("/new")
    ResponseEntity uploadImage(@RequestParam("attachment") MultipartFile multipartFile) throws IOException {
        Attachment attachment = attachmentStorage.save(multipartFile)
        LOGGER.info("Uploaded new attachment: {}", attachment)
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(attachment)
    }
}
