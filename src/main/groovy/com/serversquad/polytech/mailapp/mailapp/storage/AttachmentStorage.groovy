package com.serversquad.polytech.mailapp.mailapp.storage

import com.google.cloud.storage.Blob
import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.Attachment
import com.serversquad.polytech.mailapp.mailapp.service.FirebaseStorageService
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Component
class AttachmentStorage {

    private static final String PREFIX = "attachments"

    private final FirebaseStorageService storageService

    private final Map<Integer, String> idNameMap = new ConcurrentHashMap<>()
    private final AtomicInteger idGenerator = new AtomicInteger()

    AttachmentStorage(FirebaseStorageService storageService) {
        this.storageService = storageService
    }

    Attachment save(MultipartFile file) throws IOException {
        String name = file.getOriginalFilename()
        Blob blob = storageService.store(PREFIX, name, file.getBytes())
        Attachment attachment = new Attachment()
        attachment.setName(name)
        return new Attachment()
    }

    Attachment getByName(String name) {
        return null
    }

    Attachment getById(int id) {
        return null
    }
}
