package com.serversquad.polytech.mailapp.mailapp.repository.bodyformat

import com.serversquad.polytech.mailapp.mailapp.model.mail.BodySchema
import org.springframework.stereotype.Repository

@Repository
class BodySchemaRepository {

    private final Map<String, String> nameUrlMap

    BodySchemaRepository(Map<String, String> nameUrlMap) {
        this.nameUrlMap = nameUrlMap
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
}
