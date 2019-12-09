package com.serversquad.polytech.mailapp.mailapp.repository.email;

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmailRepository {

    /**
     * Save the given email into the repository
     * @param email the email to save
     * @return the saved email
     */
    public StoredEmail saveEmail(StoredEmail email) throws IOException;

    /**
     * Retrieve all emails with the given expeditor
     * @return all email with the given expeditor
     */
    public List<StoredEmail> getAll();

    /**
     * Retrieve the email with the given ID if it exists
     * @param id the id of the email
     * @return an Optional containing the email with the given id, if it exists
     */
    public Optional<StoredEmail> getById(int id);

    /**
     * Retrieve all emails with the given expeditor
     * @param expeditor the expeditor of the email
     * @return all email with the given expeditor
     */
    public List<StoredEmail> getAllByExpeditor(String expeditor);

    /**
     * Retrieve all emails with that has the given participant in the email or its history
     * @param email the participant email
     * @return all email with the given expeditor
     */
    public List<StoredEmail> getAllByParticipant(String email);

}