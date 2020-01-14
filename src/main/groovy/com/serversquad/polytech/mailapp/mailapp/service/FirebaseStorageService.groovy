package com.serversquad.polytech.mailapp.mailapp.service

import com.google.cloud.storage.Blob
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Bucket
import com.google.cloud.storage.StorageException
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import java.util.stream.Stream
import java.util.stream.StreamSupport

@Profile("firebase")
@Service
class FirebaseStorageService {

    private final Bucket bucket

  FirebaseStorageService(Bucket bucket) {
        this.bucket = bucket
  }

  Blob store(String prefix, String name, String data) throws IOException {
        try {
            BlobId blobId = BlobId.of(bucket.getName(), "$prefix/$name")
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/xml").build()
            Blob blob =  bucket.storage.create(blobInfo, data.getBytes(StandardCharsets.UTF_8))
            return blob
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
