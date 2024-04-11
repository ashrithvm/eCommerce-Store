package com.puzzlesapi.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import com.puzzlesapi.model.Puzzle;

@Tag("dto-tier")
public class PuzzleItemDTOTest {
    @Test
    public void testCtor() {
        // Setup
        Object[] expectedValues = {new Puzzle(), 0};

        // Invoke
        PuzzleItemDTO puzzle = new PuzzleItemDTO();
        Object[] actualValues = {puzzle.getPuzzle(), puzzle.getQuantity()};
        // Analyze
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], actualValues[i]);
        }
    }

   
    @Test
    public void testToString() {
        // Setup
        Puzzle item = new Puzzle();
        int quantity = 2;
        String expected_string = "PuzzleItemDTO{puzzle=P[0, Default, 0, Default, 0.00, EASY, Default], quantity=2}";

        PuzzleItemDTO puzzle = new PuzzleItemDTO(item, quantity);

        // Invoke
        String actual_string = puzzle.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
}