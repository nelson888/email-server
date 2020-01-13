package com.serversquad.polytech.mailapp.mailapp.repository.email

import com.serversquad.polytech.mailapp.mailapp.excepetion.SaveException
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.service.EmailParser
import org.springframework.beans.factory.annotation.Value

class LocalStorageEmailRepository extends AbstractEmailRepository {

    private final EmailParser emailParser

    // utiliser le meme dossier pour les deux
    @Value('${local.storage.root.in.path}')
    private String rootInPath
    @Value('${local.storage.root.out.path}')
    private String rootOutPath
    private final File rootIn
    private final File rootOut
    LocalStorageEmailRepository(EmailParser emailParser) {
        this.emailParser = emailParser
        rootIn = new File(rootInPath)
        checkRoot(rootIn)
        rootOut = new File(rootOutPath)
        checkRoot(rootOut)
    }

    private static void checkRoot(File root) {
        if (!root.exists() || !root.isDirectory()) {
            throw new RuntimeException("${root.path} isn't a directory")
        }
    }

    @Override
    List<StoredEmail> getAll() {
        return rootIn.listFiles()
        .findAll {it.name.endsWith(".xml")}
        .collect { emailParser.parseEmail(it.text) }
        .toList()
    }

    @Override
    StoredEmail saveEmail(StoredEmail email) throws IOException {
        File file = new File(rootOut, "${email.uuid}.xml")
        if (!file.exists() && !file.createNewFile()) {
            throw new SaveException("Couldn't save file")
        }
        file.text = emailParser.toString(email)
        return email
    }

    @Override
    Optional<StoredEmail> getByUUID(String uuid) {
        File file = new File(rootIn, "${uuid}.xml")
        if (!file.exists()) {
            return Optional.empty()
        }
        return Optional.of(emailParser.parseEmail(file.text))
    }

}
