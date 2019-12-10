package com.serversquad.polytech.mailapp.mailapp.service

import com.serversquad.polytech.mailapp.mailapp.model.converter.XSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.FrontEmail
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import org.junit.Test

import static org.junit.Assert.assertEquals

class EmailParserTest {

    private final EmailParser emailParser = new EmailParser()

    @Test
    void test() {
        StoredEmail storedEmail = emailParser.parseEmail(EmailParserTest.class.getResourceAsStream("/mail.xml").text)
        FrontEmail frontEmail = storedEmail.toFrontEmail()
        println(emailParser.toString(storedEmail))
    }

    @Test
    void parseTest() throws Exception {
        StoredEmail storedEmail = emailParser.parseEmail(EmailParserTest.class.getResourceAsStream("/mail.xml").text)
        assertEquals("2019-01-23T13:42:10", new XSDateConverter().format(storedEmail.getCreationDate()))

        StoredEmail reparsedEmail = emailParser.parseEmail(emailParser.toString(storedEmail))
        assertEquals(storedEmail, reparsedEmail)
    }

}
