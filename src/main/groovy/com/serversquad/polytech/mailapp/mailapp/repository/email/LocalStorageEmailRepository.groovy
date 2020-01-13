package com.serversquad.polytech.mailapp.mailapp.repository.email

import com.serversquad.polytech.mailapp.mailapp.excepetion.SaveException
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.service.EmailParser
import org.springframework.beans.factory.annotation.Value

class LocalStorageEmailRepository extends AbstractEmailRepository {

    private final EmailParser emailParser

    @Value('${local.storage.root.path}')
    private String rootPath
    private final File root
    LocalStorageEmailRepository(EmailParser emailParser) {
        this.emailParser = emailParser
        root = new File(rootPath)
        if (!root.exists() || !root.isDirectory()) {
            throw new RuntimeException("$rootPath isn't a directory")
        }
    }

    @Override
    List<StoredEmail> getAll() {
        return root.listFiles()
        .findAll {it.name.endsWith(".xml")}
        .collect { emailParser.parseEmail(it.text) }
        .toList()
    }

    @Override
    StoredEmail saveEmail(StoredEmail email) throws IOException {
        File file = new File(root, "${email.uuid}.xml")
        if (!file.exists() && !file.createNewFile()) {
            throw new SaveException("Couldn't save file")
        }
        file.text = emailParser.toString(email)
        return email
    }

    @Override
    Optional<StoredEmail> getByUUID(String uuid) {
        File file = new File(root, "${uuid}.xml")
        if (!file.exists()) {
            return Optional.empty()
        }
        return Optional.of(emailParser.parseEmail(file.text))
    }

}
