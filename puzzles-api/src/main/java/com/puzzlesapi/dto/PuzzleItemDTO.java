package com.puzzlesapi.dto;

import com.puzzlesapi.model.Puzzle;
import org.springframework.stereotype.Component;

/**
 * Represents a puzzle and the quantity of that puzzle. Used for client-server communication.
 */
public class PuzzleItemDTO {
    private Puzzle puzzle;
    private int quantity;

    public PuzzleItemDTO(Puzzle puzzle, int quantity){
        this.puzzle = puzzle;
        this.quantity = quantity;
    }

    public PuzzleItemDTO() {
        this(new Puzzle(), 0);
    }
    public Puzzle getPuzzle() {
        return puzzle;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString(){
        return String.format("PuzzleItemDTO{puzzle=%s, quantity=%d}", puzzle, quantity);
    }

    @Override
    public boolean equals(Object o){
        return o instanceof PuzzleItemDTO puzzleItem && puzzleItem.puzzle.equals(puzzle) && ((PuzzleItemDTO) o).quantity == quantity;
    }

    @Override
    public int hashCode(){
        return puzzle.hashCode();
    }
}
