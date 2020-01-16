package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.serversquad.polytech.mailapp.mailapp.excepetion.MalformedXmlException
import com.serversquad.polytech.mailapp.mailapp.repository.email.body.AbstractBodyRepository
import com.serversquad.polytech.mailapp.mailapp.service.XmlValidator

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

class BodySchema {

    String name
    String url
    String xsd


    void validate(String xml) throws MalformedXmlException {
        XmlValidator.validate(xsd, AbstractBodyRepository.xmlContent(xml, name))
    }
}
