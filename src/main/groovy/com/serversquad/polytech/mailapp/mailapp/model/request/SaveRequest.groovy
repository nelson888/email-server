package com.serversquad.polytech.mailapp.mailapp.model.request

class SaveRequest {

    String sender
    String object
    String body
    List<String> normalReceptors
    List<String> ccReceptors
    List<String> cciReceptors
    List<String> attachments
}
