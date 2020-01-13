package com.serversquad.polytech.mailapp.mailapp.repository.email

import com.serversquad.polytech.mailapp.mailapp.model.mail.StoredEmail

interface EmailRepository {

    /**
     * Save the given email into the repository
     * @param email the email to save
     * @return the saved email
     */
    StoredEmail saveEmail(StoredEmail email) throws IOException;

    /**
     * Retrieve all emails with the given expeditor
     * @return all email with the given expeditor
     */
    List<StoredEmail> getAll();

    /**
     * Retrieve the email with the given ID if it exists
     * @param id the id of the email
     * @return an Optional containing the email with the given id, if it exists
     */
    Optional<StoredEmail> getById(int id);


    /**
     * Retrieve the email with the given UUID if it exists
     * @param id the id of the email
     * @return an Optional containing the email with the given id, if it exists
     */
    Optional<StoredEmail> getByUUID(String uuid);

    /**
     * Retrieve all emails with the given expeditor
     * @param expeditor the expeditor of the email
     * @return all email with the given expeditor
     */
    List<StoredEmail> getAllByExpeditor(String expeditor);

    /**
     * Retrieve all emails with that has the given participant in the email or its history
     * @param email the participant email
     * @return all email with the given expeditor
     */
    List<StoredEmail> getAllByParticipantId(String id);
    /**
     * Retrieve all emails with that has the given participant in the email or its history
     * @param email the participant email
     * @return all email with the given expeditor
     */
    List<StoredEmail> getAllByParticipantName(String name);


}