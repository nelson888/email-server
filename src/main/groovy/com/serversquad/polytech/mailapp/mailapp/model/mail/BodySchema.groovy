package com.serversquad.polytech.mailapp.mailapp.model.mail

import com.serversquad.polytech.mailapp.mailapp.excepetion.MalformedXmlException

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

class BodySchema {

    private static final String PREFIX = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<body xmlns=\"polytech/app5/xm-mail/body/polytech/0.0.1\">"
    private static final String SUFFIX = "</body>"
    String name
    String xsd


    boolean validate(String xml) throws MalformedXmlException {
        try {
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema((new StreamSource(new StringReader(xsd))))
                    .newValidator()
                    .validate( new StreamSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                            "<body xmlns=\"polytech/app5/xm-mail/body/polytech/0.0.1\">$xml, \n" +
                            " \n" +
                            "</body>")))
        } catch (Exception e) {
            throw new MalformedXmlException(e)
        }

    }
}
