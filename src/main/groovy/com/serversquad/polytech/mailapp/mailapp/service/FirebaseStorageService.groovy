package com.serversquad.polytech.mailapp.mailapp.service

import com.google.cloud.storage.Blob
import com.google.cloud.storage.Bucket
import com.google.cloud.storage.StorageException
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

import java.util.stream.Stream
import java.util.stream.StreamSupport

@Profile("firebase")
@Service
class FirebaseStorageService {

    private final Bucket bucket

  FirebaseStorageService(Bucket bucket) {
        this.bucket = bucket
  }

  Blob store(String prefix, String name, byte[] data) throws IOException {
        try {
            return bucket.create(prefix + '/' + name, data)
        } catch (StorageException e) {
            throw new IOException("Error while creating blob", e)
        }
    }

  Blob get(String prefix, String name) throws IOException {
        try {
            return bucket.get(prefix + '/' + name)
        } catch (StorageException e) {
            throw new IOException("Error while retrieving blob", e)
        }
    }

  Stream<Blob> blobStream() {
        return StreamSupport.stream(bucket.list().getValues().spliterator(), false)
  }
}
