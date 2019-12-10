package com.serversquad.polytech.mailapp.mailapp.model.mail.historic

import com.serversquad.polytech.mailapp.mailapp.model.mail.historic.attachment.Attachment
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.xml.MarkupBuilder

@ToString(includePackage = false, includeFields = true, includeNames = true)
@EqualsAndHashCode
class Historic {

    List<Message> messages
  List<Attachment> attachments

  void writeXml(MarkupBuilder xml) {
        xml.historic {
            messages() {
                messages.each { m -> m.writeXml(xml) }
            }
            attachments() {
                attachments.each { a -> a.writeXml(xml) }
            }
        }
    }
}
