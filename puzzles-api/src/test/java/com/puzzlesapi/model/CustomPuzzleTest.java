package com.puzzlesapi.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Model-tier")
public class CustomPuzzleTest {
    @Test
    public void setPriceBasedOnDifficulty() {
        CustomPuzzle puzzle1 = new CustomPuzzle(0, 1, "Custom Puzzle", 1, Difficulty.EASY, "expected_imageURL");
        CustomPuzzle puzzle2 = new CustomPuzzle(0, 1, "Custom Puzzle", 1, Difficulty.MEDIUM, "expected_imageURL");
        CustomPuzzle puzzle3 = new CustomPuzzle(0, 1, "Custom Puzzle", 1, Difficulty.HARD, "expected_imageURL");

        assertEquals(puzzle1.getPrice(), 50);
        assertEquals(puzzle2.getPrice(), 70);
        assertEquals(puzzle3.getPrice(), 100);


        
    }

    @Test
    public void testHash() {
        // Setup
        CustomPuzzle puzzle = new CustomPuzzle(0, 1, "Custom Puzzle", 1, Difficulty.EASY, "expected_imageURL");


        // Invoke
        int actual = puzzle.hashCode();

        // Analyze
        assertEquals(puzzle.getId(), actual);
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        int accoundID = 23;
        int quantity = 1;
        Difficulty difficulty = Difficulty.MEDIUM;
        String imageURL = "imageURL";

        CustomPuzzle puzzle = new CustomPuzzle(id, accoundID, name, quantity, difficulty, imageURL);
        String expected_string = "CustomPuzzle{id=99, accountID=23, name=Wi-Fire, quantity=1, difficulty=MEDIUM, price=70, imageURL=imageURL}";

        // Invoke
        String actual_string = puzzle.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }

}