package com.serversquad.polytech.mailapp.mailapp.repository.bodyformat

import com.serversquad.polytech.mailapp.mailapp.model.mail.BodySchema
import org.springframework.stereotype.Repository

@Repository
class BodySchemaRepository {

    private final Map<String, String> nameUrlMap
    private final Map<String, String> urlNameMap

    BodySchemaRepository(Map<String, String> nameUrlMap) {
        this.nameUrlMap = nameUrlMap
        this.urlNameMap = [:]
        nameUrlMap.each {
            urlNameMap.put(it.value, it.key)
        }
    }

    Optional<BodySchema> getByName(String name) {
        String url = nameUrlMap.get(name)
        if (!url) {
            return Optional.empty()
        }
        return Optional.of(
                new BodySchema(name: name,
                        xsd: url.toURL().text
                ))
    }

    List<BodySchema> getAll() {
        return nameUrlMap.entrySet()
        .collect { def entry ->
            new BodySchema(name: entry.key, xsd: entry.value.toURL().text) }
        .toList()
    }

    String getByUrl(String name) {
        urlNameMap.get(name)
    }
}
