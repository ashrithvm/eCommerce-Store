package com.puzzlesapi.persistence;

import com.puzzlesapi.model.Account;

import java.io.IOException;

/**
 * Defines the functionality that any implementation of an Account Data Access Object must provide.
 * This interface is used to abstract the data access layer from the business logic layer.
 * It extends the BasicDAO interface, which defines the basic CRUD operations.
 */
public interface AccountDAO extends BasicDAO<Account> {

    /**
     * Retrieves the {@linkplain Account account} with the given username
     * @param username The username of the {@link Account account} to get
     * @return an {@link Account account} object with the matching username
     */
     Account get(String username) throws IOException;
}
