package com.puzzlesapi.persistence;

import com.puzzlesapi.model.ModelTemplate;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Defines the functionality for any BasicDAO implementation. This interface is used to
 * provide CRUD operations for any model object.
 * @param <T> The type of the model object eg. Account, Puzzle, etc.
 */
interface BasicDAO<T extends ModelTemplate> {

    /**
     * Retrieves all model objects.
     * @return An ArrayList of model objects, may be empty
     */
    ArrayList<T> getAll() throws IOException;

    /**
     * Retrieves a model object with the given id.
     * @param id The id of the model object to get
     * @return a model object with the matching id
     * <br>
     * null if no model object with a matching id is found
     */
    T get(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain T object} It uses the exact object passed and saves it. So, the id logic must be handled by the specific
     * implementation of this method.
     * @param model The model object to be created and saved
     * <br>
     * The id of the model object is ignored and a new unique id is assigned
     * @return new model object if successful, null otherwise
     */
    T create(T model) throws IOException;

    /**
     * Creates a copy of the model object but with the given id rather than the original id.
     * @param model The model object to be copied
     * @param id The id of the new model object
     * @return new model object if successful, null otherwise
     */
    T createCopyWithId(T model, int id);

    /**
     * Updates and saves a model object.
     * @param model The model object to be updated and saved
     * @return updated model object if successful, null otherwise
     */
    T update(T model) throws IOException;

    /**
     * Deletes a model object with the given id.
     * @param id The id of the model object to delete
     * @return true if the model object was deleted, false otherwise
     */

    boolean delete(int id) throws IOException;

    /**
     * Finds all model objects whose name contains the given text.
     * @param text The text to match against
     * @return An ArrayList of model objects whose names contain the given text, may be empty
     */
    default ArrayList<T> find(String text){
        throw new UnsupportedOperationException("find() is not supported by this DAO");
    }
}
