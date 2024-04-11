package com.puzzlesapi.storage;

import java.io.IOException;

/**
 * Defines the interface for storage functionality.
 * Provides the ability to load and save data from a storage location.
 * Also provides the ability to get the next id to be used for a new object.
 */
public interface Storage {

    /**
     * Loads the data from the storage (a JSON file) in memory.
     * @return true if the data was loaded successfully, false otherwise
     */
    boolean load() throws IOException;
    /**
     * Saves the data from a source location to a provided storage location.
     * @return true if the data was saved successfully, false otherwise
     */
    boolean save() throws IOException;

    /**
     * Gets the next id to be used for a new object.
     * @return the new id
     */
    int getNextId();
}
