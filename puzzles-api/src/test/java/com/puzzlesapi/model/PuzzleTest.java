package com.puzzlesapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Puzzle class
 *
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class PuzzleTest {
    @Test
    public void testCtor() {
        // Setup
        Object[] expectedValues = {99, "Wi-Fire", "Description", 50.0, Difficulty.MEDIUM, "imageURL"};
        int expected_id = (int) expectedValues[0];
        String expected_name = (String) expectedValues[1];
        int expected_quantity = 1;
        String expected_description = (String) expectedValues[2];
        double expected_price = (double) expectedValues[3];
        Difficulty expected_difficulty = (Difficulty) expectedValues[4];
        String expected_imageURL = (String) expectedValues[5];

        // Invoke
        Puzzle puzzle = new Puzzle(expected_id, expected_name, expected_quantity, expected_description, expected_price, expected_difficulty, expected_imageURL);
        Object[] actualValues = {puzzle.getId(), puzzle.getName(), puzzle.getDescription(), puzzle.getPrice(), puzzle.getDifficulty(), puzzle.getImageURL()};
        // Analyze
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], actualValues[i]);
        }
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        Puzzle puzzle = new Puzzle(id, name, 23, "Description", 50.0, Difficulty.MEDIUM, "imageURL");

        String expected_name = "Galactic Agent";

        // Invoke
        puzzle.setName(expected_name);

        // Analyze
        assertEquals(expected_name, puzzle.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        int quantity = 23;
        String description = "Description";
        double price = 50.0;
        Difficulty difficulty = Difficulty.MEDIUM;
        String imageURL = "imageURL";
        String expected_string = String.format(Puzzle.STRING_FORMAT, id, name, quantity, description, price, difficulty, imageURL);

        Puzzle puzzle = new Puzzle(id, name, quantity, description, price, difficulty, imageURL);

        // Invoke
        String actual_string = puzzle.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }

    @Test
    public void testEqualsNotPuzzle(){
        Object[] notPuzzle = {98, "test_puzzle"};

        int id = 99;
        String name = "Wi-Fire";
        int quantity = 23;
        String description = "Description";
        double price = 50.0;
        Difficulty difficulty = Difficulty.MEDIUM;
        String imageURL = "imageURL";
        Puzzle puzzle = new Puzzle(id, name, quantity, description, price, difficulty, imageURL);

        assertFalse(puzzle.equals(notPuzzle));

    }

    @Test
    public void testCompareTo(){
        int id = 99;
        String name = "Wi-Fire";
        int quantity = 23;
        String description = "Description";
        double price = 50.0;
        Difficulty difficulty = Difficulty.MEDIUM;
        String imageURL = "imageURL";

        Puzzle puzzle1 = new Puzzle(id, name, quantity, description, price, difficulty, imageURL);

        int id_2 = 94;
        String name_2 = "Wi-Fire";
        int quantity_2 = 22;
        String description_2 = "Descriptionnn 2";
        double price_2 = 23.0;
        Difficulty difficulty_2 = Difficulty.EASY;
        String imageURL_2 = "imageURL2";
        Puzzle puzzle2 = new Puzzle(id_2, name_2, quantity_2, description_2, price_2, difficulty_2, imageURL_2);

        int expected = 0;

        assertEquals(expected, puzzle1.compareTo(puzzle2));
    }

    @Test
    public void testGetHash(){
        int id = 99;
        String name = "Wi-Fire";
        int quantity = 23;
        String description = "Description";
        double price = 50.0;
        Difficulty difficulty = Difficulty.MEDIUM;
        String imageURL = "imageURL";
        Puzzle puzzle = new Puzzle(id, name, quantity, description, price, difficulty, imageURL);

        int expected = 99;

        assertEquals(expected, puzzle.hashCode());

    }

    @Test
    public void testEquals(){
        int id_1 = 99;
        String name_1 = "Wi-Fire";
        Puzzle puzzle1 = new Puzzle(id_1, name_1, 23, "Description", 50.0, Difficulty.MEDIUM, "imageURL");

        int id_2 = 90;
        String name_2 = "Wi-Fire";
        Puzzle puzzle2 = new Puzzle(id_2, name_2, 23, "Description", 50.0, Difficulty.MEDIUM, "imageURL");

        int id_3 = 99;
        String name_3 = "Galactic Agent";
        Puzzle puzzle3 = new Puzzle(id_3, name_3, 23, "Description", 50.0, Difficulty.MEDIUM, "imageURL");

        assertTrue(puzzle1.equals(puzzle2));
        assertTrue(puzzle1.equals(puzzle3));
        assertFalse(puzzle2.equals(puzzle3));

    }
}
