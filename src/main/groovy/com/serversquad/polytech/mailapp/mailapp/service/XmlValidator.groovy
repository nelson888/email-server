package com.serversquad.polytech.mailapp.mailapp.service

import com.serversquad.polytech.mailapp.mailapp.excepetion.MalformedXmlException

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

class XmlValidator {

    private static StreamSource stringSource(String s) {
        return new StreamSource(new ByteArrayInputStream(
                s.bytes
        ))
    }

    static void validateFromXsdUrl(String xsdUrl, String xmlContent) throws MalformedXmlException {
        validate(xsdUrl.toURL().text, xmlContent)
    }

    static void validate(String xsd, String xmlContent) throws MalformedXmlException {
        try {
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(stringSource(xsd))
                    .newValidator()
                    .validate(stringSource(xmlContent))
        } catch (Exception e) {
            throw new MalformedXmlException(e)
        }
    }
}
