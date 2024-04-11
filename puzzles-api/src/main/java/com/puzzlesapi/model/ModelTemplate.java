package com.puzzlesapi.model;

/**
 * A template for all model objects. Provides a unique id for each object.
 * Every model class should extend it if it needs storage functionality.
 */
public class ModelTemplate {
    private final int id;
    public ModelTemplate(int id){
        this.id = id;
    }
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj){
        return obj instanceof ModelTemplate m && m.getId() == this.getId();
    }

    @Override
    public int hashCode(){
        return id;
    }

}
