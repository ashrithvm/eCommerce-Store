package com.puzzlesapi.dto;

import com.puzzlesapi.model.Account;

import java.util.List;

/**
 * Data Transfer Object for the Cart model.
 */
public class CartDTO {
    private int id;
    private List<PuzzleItemDTO> items;
    private List<CustomPuzzleDTO> customPuzzles;

    public CartDTO(int id, List<PuzzleItemDTO> items, List<CustomPuzzleDTO> customPuzzles) {
        this.id = id;
        this.items = items;
        this.customPuzzles = customPuzzles;
    }

    public CartDTO(){
        this(0, List.of(), List.of());
    }

    public int getId() {
        return id;
    }

    public List<PuzzleItemDTO> getItems() {
        return items;
    }

    public List<CustomPuzzleDTO> getCustomPuzzles() {
        return customPuzzles;
    }

}
