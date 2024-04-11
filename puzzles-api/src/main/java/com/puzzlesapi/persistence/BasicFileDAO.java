package com.puzzlesapi.persistence;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.model.ModelTemplate;
import com.puzzlesapi.storage.FileStorage;
import com.puzzlesapi.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Provides functionality for all CRUD operations for any FileDAO implementation to use.
 * Must be extended by the FileDAO implementation to get the functionality.
 * @param <T> The type of the model object
 */
abstract class BasicFileDAO<T extends ModelTemplate> implements BasicDAO<T> {

    /**
     * Local cache.
     */
    private final TreeMap<Integer, T> objects;
    private final Storage storage;
    protected TreeMap<Integer, T> getObjects() {
        return objects;
    }

    /**
     * @return The next id in the sequence.
     */
    protected int getNextId() {
        return storage.getNextId();
    }

    protected BasicFileDAO(String filename, Class<T[]> type, TreeMap<Integer, T> objects, ObjectMapper mapper) throws IOException {
        this.objects = objects;
        storage = new FileStorage<>(filename, type, mapper, objects);
        storage.load();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<T> getAll() throws IOException {
        synchronized (objects) {
            return new ArrayList<>(objects.values());
        }
    }

    /**
     * {@inheritDoc}
     */
    public T get(int id) throws IOException {
        synchronized (objects) {
            return objects.getOrDefault(id, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T create(T model) throws IOException{
        T newObject = createCopyWithId(model, getNextId());
        synchronized (objects) {
            objects.put(newObject.getId(), newObject);
            storage.save();
            return newObject;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(T model) throws IOException {
        if (!objects.containsKey(model.getId()))
            return null;

        synchronized (objects) {
            objects.put(model.getId(), model);
            storage.save();
            return model;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(int id) throws IOException {
        synchronized (objects) {
            if (objects.remove(id) != null) {
                storage.save();
                return true;
            }
            return false;
        }
    }

    protected boolean save() throws IOException {
        return storage.save();
    }
}
