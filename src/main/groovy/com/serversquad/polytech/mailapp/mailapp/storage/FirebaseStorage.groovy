package com.serversquad.polytech.mailapp.mailapp.storage

import com.google.cloud.storage.Blob
import com.serversquad.polytech.mailapp.mailapp.service.EmailParser
import com.serversquad.polytech.mailapp.mailapp.service.FirebaseStorageService
import groovy.util.logging.Slf4j

import java.util.stream.Collectors

@Slf4j('LOGGER')
abstract class FirebaseStorage<T, Id> {

    private final String prefixPath
    protected final FirebaseStorageService storageService
    protected final EmailParser emailParser

    FirebaseStorage(String prefixPath, FirebaseStorageService storageService, EmailParser emailParser) {
        this.prefixPath = prefixPath
        this.storageService = storageService
        this.emailParser = emailParser
    }

    Optional<T> getById(Id id) {
        final String prefixPath = prefixPath // apparently avoid crashing
        try {
            Blob blob = storageService.get(prefixPath, fileNameForId(id))
            return Optional.ofNullable(blob)
                    .map(this.&parse)
        } catch (IOException e) {
            LOGGER.error("Error while retreiving email with id " + id, e)
            return Optional.empty()
        }
    }

    List<T> getAll() {
        final String prefixPath = prefixPath // apparently avoid crashing
        return storageService.blobStream()
                .filter({ b -> b.getName().contains(prefixPath + "/") && b.getName().endsWith(".xml") })
                .map(this.&parse)
                .collect(Collectors.toList())
    }

    abstract T save(T object)
    protected abstract T parse(Blob blob)
    protected abstract String fileNameForId(Id id)
}
