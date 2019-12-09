package com.serversquad.polytech.mailapp.mailapp.repository.email;

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail;
import com.serversquad.polytech.mailapp.mailapp.storage.EmailStorage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class FirebaseEmailRepository implements EmailRepository {

  private final EmailStorage emailStorage;
  private final AtomicInteger idGenerator = new AtomicInteger();

  public FirebaseEmailRepository(EmailStorage emailStorage) {
    this.emailStorage = emailStorage;
  }

  @Override
  public StoredEmail saveEmail(StoredEmail email) throws IOException {
    if (email.getId() == null) {
      email.setId(idGenerator.getAndIncrement());
    }
    return emailStorage.save(email);
  }

  @Override
  public List<StoredEmail> getAll() {
    return emailStorage.getAll();
  }

  @Override
  public Optional<StoredEmail> getById(int id) {
    return emailStorage.getById(id);
  }

  @Override
  public List<StoredEmail> getAllByExpeditor(String expeditor) {
    //TODO get All emails with getAll function:
    // then filter to keep only emails with the given expeditor
    return null;
  }

  @Override
  public List<StoredEmail> getAllByParticipant(String email) {
    //TODO get All emails with getAll function:
    // then filter to keep only emails that has the given email in
    // one of the participants (see StoredEmail.getAllParticipants())
    return null;
  }
}
