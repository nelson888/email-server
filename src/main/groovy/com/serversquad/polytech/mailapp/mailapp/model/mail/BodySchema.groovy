package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.serversquad.polytech.mailapp.mailapp.excepetion.MalformedXmlException

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

class BodySchema {

    String name
    String url
    String xsd


    void validate(String xml) throws MalformedXmlException {
        try {
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new URL(url))
                    .newValidator()
                    .validate(new StreamSource(xml)) // TODO find a way to put full content in xml (starting tag)
        } catch (Exception e) {
            throw new MalformedXmlException(e)
        }
    }
}
