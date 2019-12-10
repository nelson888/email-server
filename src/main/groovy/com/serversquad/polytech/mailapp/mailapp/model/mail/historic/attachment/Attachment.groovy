package com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode
class Attachment {

    String id
    String name
  String mimeType
  List<AttachmentVersion> versions

  void writeXml(MarkupBuilder xml) {
        xml.attachment(id: id, name: name, mimetype: mimeType) {
            versions.each { v -> v.writeXml(xml) }
        }
    }
}
