package com.serversquad.polytech.mailapp.mailapp.model.request

class SaveMailRequest {

    String uuid
    String emitter
    String body = ""
    String object = ""
    String bodySchema = "string"
    List<String> participants = []
    List<String> groups = []
    List<String> attachments = [] // ignored

}
