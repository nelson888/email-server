package com.serversquad.polytech.mailapp.mailapp.model.mail

import groovy.xml.MarkupBuilder

class BodyRef {
    String format
    String id

    void writeXml(MarkupBuilder xml) {
        xml.body(format: format, id: id)
    }
}
