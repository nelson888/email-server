package com.serversquad.polytech.mailapp.mailapp.repository.attachment;

import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentRepository {

  Attachment save(MultipartFile multipartFile);

}
