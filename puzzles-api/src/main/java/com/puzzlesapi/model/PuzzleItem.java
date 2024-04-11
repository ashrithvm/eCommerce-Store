package com.puzzlesapi.model;

/**
 * Represents a puzzle in the cart. It associates a puzzleId with a quantity.
 */
public class PuzzleItem implements Comparable<PuzzleItem> {
    private final int id;
    private int quantity;

    public PuzzleItem(Puzzle puzzle, int quantity){
        this.id = puzzle.getId();
        this.quantity = quantity;
    }

    public PuzzleItem(int id){
        this.id = id;
        this.quantity = 0;
    }
    public PuzzleItem(){
        this(new Puzzle(), 0);
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("[puzzleId='%d', quantity='%d']", id, quantity);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PuzzleItem p && p.id == id;
    }

    @Override
    public int compareTo(PuzzleItem puzzleCartItem) {
        return id - puzzleCartItem.id;
    }
}
