package com.serversquad.polytech.mailapp.mailapp

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import com.serversquad.polytech.mailapp.mailapp.repository.email.EmailRepository
import com.serversquad.polytech.mailapp.mailapp.service.EmailParser
import com.serversquad.polytech.mailapp.mailapp.service.EmailParserTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import static org.junit.Assert.assertNotNull

@SpringBootTest
class MailappApplicationTests {
    @Autowired
    private EmailRepository emailRepository

    private final EmailParser emailParser = new EmailParser()

    @Test
    void contextLoads() {}

    @Test
    void storeEmail() throws Exception {
        StoredEmail storedEmail = emailParser.parseEmail(EmailParserTest.class.getResourceAsStream("/mail.xml").bytes)
        emailRepository.saveEmail(storedEmail)
    }

    @Test
    void getEmail() throws Exception {
        StoredEmail storedEmail = emailRepository.getById(0).get()
        assertNotNull(storedEmail)
    }

}
