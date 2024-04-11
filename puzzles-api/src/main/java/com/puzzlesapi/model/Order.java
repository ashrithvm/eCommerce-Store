package com.puzzlesapi.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Represents an order in the system. It associates a buyerId with a list of puzzle items.
 * The total is the sum of the prices of all the puzzle items in the order.
 *
 */
public class Order extends ModelTemplate {
    private final int buyerId;
    private final List<PuzzleItem> items;
    private final List<CustomPuzzle> customPuzzles;
    private double total;

    public static String STRING_FORMAT = "Order[id=%d, buyerId=%d, total=%.2f]";

    public Order(int id, int buyerId, List<PuzzleItem> items, double total) {
        super(id);
        this.buyerId = buyerId;
        this.items = items;
        this.customPuzzles = new LinkedList<>();
        this.total = total;
    }

    public Order(int id, int buyerId, List<PuzzleItem> items, List<CustomPuzzle> customPuzzles, double total) {
        super(id);
        this.buyerId = buyerId;
        this.items = items;
        this.customPuzzles = customPuzzles;
        this.total = total;
    }

    public Order(int id, int buyerId, List<PuzzleItem> items) {
        super(id);
        this.buyerId = buyerId;
        this.items = items;
        this.customPuzzles = new ArrayList<>();
        this.total = 0;
    }

    public Order(){
        this(0, 0, new ArrayList<>(), new ArrayList<>(), 0);
    }
    public int getBuyerId() {
        return buyerId;
    }

    public List<PuzzleItem> getItems() {
        return items;
    }

    public List<CustomPuzzle> getCustomPuzzles() {
        return customPuzzles;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Adds a puzzle item to the order if not null
     * @param customPuzzle the puzzle item to add
     */
    public void addCustomPuzzle(CustomPuzzle customPuzzle) {
        if(customPuzzles != null)
            customPuzzles.add(customPuzzle);

    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Order order) {
            return order.getId() == getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, getId(), buyerId, total);
    }

}
