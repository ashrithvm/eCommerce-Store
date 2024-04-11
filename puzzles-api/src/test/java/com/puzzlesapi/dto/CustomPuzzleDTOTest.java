package com.puzzlesapi.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.Difficulty;

@Tag("dto-tier")
public class CustomPuzzleDTOTest {
    @Test
    public void testCtor() {
        // Setup
        Object[] expectedValues = {0, null, "", 0, Difficulty.EASY, 0, ""};

        // Invoke
        CustomPuzzleDTO puzzle = new CustomPuzzleDTO();
        Object[] actualValues = {puzzle.getId(), puzzle.getAccount(), puzzle.getName(), puzzle.getQuantity(),puzzle.getDifficulty(), puzzle.getPrice(), puzzle.getImageURL()};
        // Analyze
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], actualValues[i]);
        }
    }

   
    @Test
    public void testToString() {
        // Setup
        int id = 99;
        Account account = new Account(1, "user");
        String name = "Wi-Fire";
        int quantity = 23;
        int price = 50;
        Difficulty difficulty = Difficulty.MEDIUM;
        String imageURL = "imageURL";
        String expected_string = "CustomPuzzleDTO{id=99, account=Account[id=1, username='user'], name=Wi-Fire, quantity=23, difficulty=MEDIUM, price=50, imageURL=imageURL}";

        CustomPuzzleDTO puzzle = new CustomPuzzleDTO(id, account, name, quantity, difficulty, price, imageURL);

        // Invoke
        String actual_string = puzzle.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }

    @Test
    public void testEqualsNotPuzzle(){
        Object[] notPuzzle = {98, "test_puzzle"};

        int id = 99;
        Account account = new Account(1, "user");
        String name = "Wi-Fire";
        int quantity = 23;
        int price = 50;
        Difficulty difficulty = Difficulty.MEDIUM;
        String imageURL = "imageURL";
        CustomPuzzleDTO puzzle = new CustomPuzzleDTO(id, account, name, quantity, difficulty, price, imageURL);

        assertFalse(puzzle.equals(notPuzzle));

    }

}
