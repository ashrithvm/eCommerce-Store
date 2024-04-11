package com.puzzlesapi.dto;

import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.PuzzleItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Represent how the server will return an order to the client.
 * It is different from the Order class because it includes full information about the buyer or the puzzle items, not just the ids.
 * It is also used to receive an order from the client.
 */
public class OrderDTO {

    private int id;
    private Account buyer;
    private List<PuzzleItemDTO> items;
    private List<CustomPuzzleDTO> customPuzzles;
    private double total;

    public OrderDTO(int id, Account buyer, List<PuzzleItemDTO> items, List<CustomPuzzleDTO> customPuzzles, double total) {
        this.id = id;
        this.buyer = buyer;
        this.items = items;
        this.customPuzzles = customPuzzles;
        this.total = total;
    }

    public OrderDTO() {
        this(0, null, null, null,0);
    }
    public int getId() {
        return id;
    }

    public Account getBuyer() {
        return buyer;
    }

    public List<PuzzleItemDTO> getItems() {
        return items;
    }

    public List<CustomPuzzleDTO> getCustomPuzzles() {
        return customPuzzles;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString(){
        return String.format("OrderDTO{id=%d, buyer=%s, items=%s, total=%f}", id, buyer, items, total);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof OrderDTO && ((OrderDTO) obj).id == id;
    }
}
