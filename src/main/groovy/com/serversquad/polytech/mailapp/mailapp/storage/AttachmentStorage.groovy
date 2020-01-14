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


    Attachment save(MultipartFile file) throws IOException {
        return null
    }

    Attachment getByName(String name) {
        return null
    }

    Attachment getById(int id) {
        return null
    }
}
