package com.puzzlesapi.model;

import java.util.LinkedList;
import java.util.List;

public class Cart extends ModelTemplate{

    /**
     * The puzzles in the cart
     * <br>
     * To allow having multiple quantities of a product in cart, instead of using a list of puzzles, we use a different class,
     * PuzzleItem, which is a puzzle with a quantity.
     */
    private final LinkedList<PuzzleItem> puzzles;
    private final List<CustomPuzzle> customPuzzles;
    static final String STRING_FORMAT = "Cart[id=%d, puzzles='%s, customPuzzles='%s]";

    public Cart(int id, LinkedList<PuzzleItem> puzzles, List<CustomPuzzle> customPuzzles){
        super(id);
        this.puzzles = puzzles;
        this.customPuzzles = customPuzzles;
    }
    public Cart(int id, LinkedList<PuzzleItem> puzzles){
        this(id, puzzles, new LinkedList<>());
    }
    public Cart(int id){
        this(id, new LinkedList<>());
    }
    public Cart(){
        this(0, new LinkedList<>());
    }

    public LinkedList<PuzzleItem> getPuzzles() {
        return puzzles;
    }

    public List<CustomPuzzle> getCustomPuzzles() {
        return customPuzzles;
    }

    /**
     * Checks if the cart for the account contains the puzzle
     * @param puzzle The puzzle to check for
     * @return true if the puzzle is in the cart, false otherwise
     */
    public boolean containsPuzzle(Puzzle puzzle){
        return puzzles.contains(new PuzzleItem(puzzle, 0));
    }

    /**
     * Returns a PuzzleItem object, which is basically a puzzle with quantity in the cart
     * @param puzzle The puzzle to get from the cart
     * @return The PuzzleItem object if the puzzle is in the cart, null otherwise
     */
    public PuzzleItem getPuzzleCartItem(Puzzle puzzle){
        int puzzleIndex = puzzles.indexOf(new PuzzleItem(puzzle.getId()));
        if(puzzleIndex != -1)
            return puzzles.get(puzzleIndex);
        return null;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, getId(), puzzles, customPuzzles);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Cart c){
            return c.getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId();
    }

}
