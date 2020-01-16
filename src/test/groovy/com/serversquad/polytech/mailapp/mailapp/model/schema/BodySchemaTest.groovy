package com.serversquad.polytech.mailapp.mailapp.model.schema

import com.serversquad.polytech.mailapp.mailapp.excepetion.MalformedXmlException
import org.junit.Test

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

class BodySchemaTest {


    @Test
    void test() {
        File file = new File('/home/nelson/Téléchargements/testBody.xml')
        println(file.text)
        try {
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new URL('https://rcassier.myds.me/polytech_app5/xm_mail/extensions/string.xsd'))
                    .newValidator()
                    .validate(new StreamSource(file))
        } catch (Exception e) {
            throw new MalformedXmlException(e)
        }
    }
}
