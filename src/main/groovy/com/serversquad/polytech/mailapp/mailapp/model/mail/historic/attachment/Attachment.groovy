package com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment


import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.Version
import groovy.xml.MarkupBuilder;

public class Attachment {

  String id
  String name;
  String mimeType;
  List<Version> versions;
 /* Integer size;
  String version;
  Date expirationDate;
  @JsonIgnore
  byte[] data;*/

  String getTextData() {
    return new String(data)
  }

  void writeXml(MarkupBuilder xml) {
    xml.attachment(id: id, name: name, mimetype: mimeType)//, size: size, version: version, expirationDate: expirationDate, textData)
  }
}
