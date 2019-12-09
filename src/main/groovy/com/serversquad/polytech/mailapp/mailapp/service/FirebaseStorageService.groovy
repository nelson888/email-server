package com.serversquad.polytech.mailapp.mailapp.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class FirebaseStorageService {

  private final Bucket bucket;

  public FirebaseStorageService(Bucket bucket) {
    this.bucket = bucket;
  }

  public Blob store(String prefix, String name, byte[] data) throws IOException {
    try {
      return bucket.create(prefix + '/' + name, data);
    } catch (StorageException e) {
      throw new IOException("Error while creating blob", e);
    }
  }

  public Blob get(String prefix, String name) throws IOException {
    try {
      return bucket.get(prefix + '/' + name);
    } catch (StorageException e) {
      throw new IOException("Error while retrieving blob", e);
    }
  }

  public Stream<Blob> blobStream() {
    return StreamSupport.stream(bucket.list().getValues().spliterator(), false);
  }
}
