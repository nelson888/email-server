package com.serversquad.polytech.mailapp.mailapp.repository.email.body

import groovy.xml.MarkupBuilder

abstract class AbstractBodyRepository implements BodyRepository {

    public static String xmlContent(String content, String namespace) {
        StringWriter writer = new StringWriter()
        MarkupBuilder xml = new MarkupBuilder(writer)
        xml.mkp.xmlDeclaration(version: "1.0", encoding: "UTF-8")
        xml.body(xmlns: "polytech/app5/xm-mail/body/$namespace/0.0.1" +
                "",  content)
        return writer.toString()
    }
}
