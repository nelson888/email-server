package com.serversquad.polytech.mailapp.mailapp.model.mail.historic

import groovy.xml.MarkupBuilder

class Version {


    Integer number
    Integer size
    String creator
    String insertion_moment;


    String getTextData() {
        return new String(data)
    }

    void writeXml(MarkupBuilder xml) {
    }
}
