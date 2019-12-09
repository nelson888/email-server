package com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment

import groovy.xml.MarkupBuilder

class AttachmentVersion {

    Integer number
    Integer size
    String creator
    String insertion_moment
    String content //base64

    void writeXml(MarkupBuilder xml) {
    }
}
