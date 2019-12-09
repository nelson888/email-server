package com.serversquad.polytech.mailapp.mailapp.service;

import com.serversquad.polytech.mailapp.mailapp.model.converter.JSDateConverter
import com.serversquad.polytech.mailapp.mailapp.model.mail.FrontEmail;
import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmailParserTest {
    private final EmailParser emailParser = new EmailParser();


    @Test
    public void test() {
        StoredEmail storedEmail = emailParser.parseEmail(EmailParserTest.class.getResourceAsStream("/mailv2.xml").text)
        FrontEmail frontEmail = storedEmail.toFrontEmail()
        println(emailParser.toString(storedEmail))
    }

    @Test
    public void parseTest() throws Exception {
        StoredEmail storedEmail = emailParser.parseEmail(EmailParserTest.class.getResourceAsStream("/mail.xml").text);
        assertEquals("Fri Nov 22 2019 11:48:47 GMT+0100", new JSDateConverter().format(storedEmail.getCreationDate()));

        StoredEmail reparsedEmail = emailParser.parseEmail(emailParser.toString(storedEmail))
        assertEquals(storedEmail, reparsedEmail)
    }

}
