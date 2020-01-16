package com.serversquad.polytech.mailapp.mailapp.repository.email

import com.serversquad.polytech.mailapp.mailapp.excepetion.MalformedXmlException
import com.serversquad.polytech.mailapp.mailapp.excepetion.SaveException
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.service.EmailParser
import com.serversquad.polytech.mailapp.mailapp.service.XmlValidator
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Profile("local")
@Repository
@Slf4j('LOGGER')
class LocalStorageEmailRepository extends AbstractEmailRepository {

    private final EmailParser emailParser

    private final File rootIn
    private final File rootOut

    @Value('${mail.xsd.url}')
    private String mailXsdUrl

    LocalStorageEmailRepository(EmailParser emailParser,
                                @Qualifier("emailRootIn") File rootIn,
                                @Qualifier("emailRootOut")  File rootOut) {
        this.emailParser = emailParser
        this.rootIn = rootIn
        this.rootOut = rootOut
        LOGGER.info("Found {} emails", getAll().size())
    }

    @Override
    List<StoredEmail> getAll() {
        return rootIn.listFiles()
        .findAll {it.name.endsWith(".xml")}
        .collect {
            String text = it.text
            try {
                XmlValidator.validateFromXsdUrl(mailXsdUrl, text)
            } catch (MalformedXmlException e) {
                return null
            }
            emailParser.parseEmail(text) }
        .findAll(Objects.&nonNull)
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
