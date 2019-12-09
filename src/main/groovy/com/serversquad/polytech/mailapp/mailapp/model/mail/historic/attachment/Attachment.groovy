package com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment


import groovy.xml.MarkupBuilder;

public class Attachment {

  String id
  String name;
  String mimeType;
  List<AttachmentVersion> versions;

  void writeXml(MarkupBuilder xml) {
    xml.attachment(id: id, name: name, mimetype: mimeType)//, size: size, version: version, expirationDate: expirationDate, textData)
  }
}
