package com.puzzlesapi.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.model.ModelTemplate;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

/**
 * JSON file-based implementation of the {@linkplain Storage} interface for all objects that extend {@linkplain ModelTemplate}.
 * The class provides the ability to load and save data from/to a JSON file.
 * It generates the next id to be used for a new object by
 * finding what follows the largest id.
 * @param <T> The type of object to be stored.
 */
public class FileStorage<T extends ModelTemplate> implements Storage{

    private final String filename;
    private final Class<T[]> type;
    private final ObjectMapper mapper;
    private final TreeMap<Integer, T> objects;
    private int nextId;

    /**
     * @param filename The path to the file to be read from and written to.
     * @param type An array of the type of object to be stored. E.g. If the object is Puzzle, type is: Puzzle[].class
     * @param mapper Provides JSON Object to/from Java Object serialization and deserialization.
     * @param objects The local cache of objects to be stored.
     */
    public FileStorage(String filename, Class<T[]> type, ObjectMapper mapper, TreeMap<Integer, T> objects) {
        this.filename = filename;
        this.type = type;
        this.mapper = mapper;
        this.objects = objects;
    }
    /**
     * {@inheritDoc} The id that is assigned is the largest id in the local cache + 1.
     */
    @Override
    public int getNextId() {
        int id = nextId;
        updateNextId();
        return id;
    }

    private void updateNextId() {
        if(objects.isEmpty())
            nextId = 1;
        else
            nextId = objects.lastKey() + 1;

    }

    /**
     * Loads the data from local cache (a TreeMap) to a specified file.
     * @return {@inheritDoc}
     */
    @Override
    public boolean load() throws IOException {
        objects.clear();
        nextId = 0;
        try {
            T[] arr = mapper.readValue(new File(filename), type);
            for (T obj : arr) {
                objects.put(obj.getId(), obj);
                if (obj.getId() > nextId) {
                    nextId = obj.getId();
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading from file: " + filename, e);
        }
        ++nextId;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save() throws IOException {
        try {
            mapper.writeValue(new File(filename), objects.values());
            updateNextId();
        } catch (IOException e) {
            throw new IOException("Error writing to file: " + filename, e);
        }
        return true;
    }

}
