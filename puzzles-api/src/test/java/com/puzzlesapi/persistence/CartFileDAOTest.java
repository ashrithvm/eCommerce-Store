package com.puzzlesapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.Cart;
import com.puzzlesapi.model.CustomPuzzle;
import com.puzzlesapi.model.Difficulty;
import com.puzzlesapi.model.Puzzle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Persistence-tier")
class CartFileDAOTest {
    CartFileDAO cartFileDAO;
    ArrayList<Cart> testCarts;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    void setupCartFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        Account[] testAccounts = new Account[5];
        for (int i = 0; i < 5; i++) {
            testAccounts[i] = new Account(i + 1, "username" + i);
        }
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Account[].class))
                .thenReturn(testAccounts);
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Cart[].class))
                .thenReturn(new Cart[] {});
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Puzzle[].class))
                .thenReturn(new Puzzle[] {new Puzzle("P1", 20.00, 3)});
        cartFileDAO = new CartFileDAO("doesnt_matter.txt", mockObjectMapper, new PuzzleFileDAO("doesnt_matter.txt", mockObjectMapper));

        //when AccountFileDAO is created, Spring injects CartFileDAO so that it can be used to create a cart for each account.
        AccountFileDAO accountFileDAO = new AccountFileDAO("doesnt_matter.txt", mockObjectMapper, cartFileDAO);
        testCarts = cartFileDAO.getAll();
    }

    @Test
    void testGetCarts() throws IOException {
        System.out.println(cartFileDAO.getAll());
        System.out.println(testCarts);
        System.out.println(cartFileDAO.getAll());
        assertEquals(5, cartFileDAO.getAll().size());
    }

    @Test
    void testAddPuzzle() throws IOException {
        cartFileDAO.addPuzzle(testCarts.get(0), new Puzzle());
        System.out.println(testCarts.get(0).getPuzzles());
        System.out.println(cartFileDAO.getAll());
        assertEquals(1, testCarts.get(0).getPuzzles().size());
    }

    @Test
    void testAddPuzzleTwice() throws IOException {
        Puzzle puzzle = new Puzzle(0, null, 10, null, 0, null, null);
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle);
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle);
        assertEquals(2, cartFileDAO.get(1).getPuzzleCartItem(puzzle).getQuantity());
    }

    @Test
    void testAddPuzzleWithQuantity() throws IOException {
        Puzzle puzzle = new Puzzle(0, null, 10, null, 0, null, null);
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle);
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle, 3);
        assertEquals(4, cartFileDAO.get(1).getPuzzleCartItem(puzzle).getQuantity());
    }

    @Test
    void testAddPuzzleById() throws IOException {
        cartFileDAO.addPuzzle(testCarts.get(0).getId(), 0, 1);
        assertTrue(testCarts.get(0).containsPuzzle(new Puzzle()));
    }
    @Test
    void testAddPuzzleWithNullCart() throws IOException {
        Puzzle puzzle = new Puzzle();
        cartFileDAO.addPuzzle(null, puzzle);
        assertEquals(0, testCarts.get(0).getPuzzles().size());
    }

    @Test
    void testAddPuzzleWithNullPuzzle() throws IOException {
        cartFileDAO.addPuzzle(testCarts.get(0), null);
        assertEquals(0, testCarts.get(0).getPuzzles().size());
    }

    @Test
    void testRemovePuzzle() throws IOException {
        Puzzle puzzle = new Puzzle(0, null, 10, null, 0, null, null);
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle);
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle);
        cartFileDAO.removePuzzle(testCarts.get(0), puzzle);
        assertEquals(0, testCarts.get(0).getPuzzles().size());
    }

    @Test
    void testRemovePuzzleWithQuantity() throws IOException {
        Puzzle puzzle = new Puzzle(0, null, 10, null, 0, null, null);
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle);
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle);
        cartFileDAO.removePuzzle(testCarts.get(0), puzzle, 1);
        assertEquals(1, testCarts.get(0).getPuzzleCartItem(puzzle).getQuantity());
    }

    @Test
    void testRemovePuzzleWithNullCart() throws IOException {
        Puzzle puzzle = new Puzzle();
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle);
        cartFileDAO.removePuzzle(null, puzzle);
        assertEquals(1, testCarts.get(0).getPuzzles().size());
    }

    @Test
    void testRemovePuzzleWithNullPuzzle() throws IOException {
        Puzzle puzzle = new Puzzle();
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle);
        cartFileDAO.removePuzzle(testCarts.get(0), null);
        assertEquals(1, testCarts.get(0).getPuzzles().size());
    }

    @Test
    void testRemovePuzzleWithNullCartAndPuzzle() throws IOException {
        cartFileDAO.removePuzzle(null, null);
        assertEquals(0, testCarts.get(0).getPuzzles().size());
    }

    @Test
    void testRemovePuzzleWithQuantityGreaterThanQuantity() throws IOException {
        Puzzle puzzle = new Puzzle();
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle);
        cartFileDAO.removePuzzle(testCarts.get(0), puzzle, 2);
        assertEquals(0, testCarts.get(0).getPuzzles().size());
    }

    @Test
    void testRemovePuzzleWithQuantityEqualToQuantity() throws IOException {
        Puzzle puzzle = new Puzzle();
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle);
        cartFileDAO.removePuzzle(testCarts.get(0), puzzle, 1);
        assertEquals(0, testCarts.get(0).getPuzzles().size());
    }

    @Test
    void testRemovePuzzleWithQuantityLessThanQuantity() throws IOException {
        Puzzle puzzle = new Puzzle();
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle);
        assertThrows(IllegalArgumentException.class, () -> cartFileDAO.removePuzzle(testCarts.get(0), puzzle, 0));
    }

    @Test
    void testRemovePuzzleWithQuantityLessThanZero() throws IOException {
        Puzzle puzzle = new Puzzle();
        cartFileDAO.addPuzzle(testCarts.get(0), puzzle);
        assertThrows(IllegalArgumentException.class, () -> cartFileDAO.removePuzzle(testCarts.get(0), puzzle, -1));
    }

    @Test
    void testRemoveById() throws IOException {
        Puzzle puzzle = new Puzzle();
        cartFileDAO.removePuzzle(testCarts.get(0).getId(), puzzle.getId());
        System.out.println(testCarts.get(0).getPuzzles());
        assertFalse(testCarts.get(0).containsPuzzle(puzzle));
    }

    @Test
    void testFind(){
        assertThrows( UnsupportedOperationException.class, () -> cartFileDAO.find("text"));
    }

    @Test
    void testUpdateCart() throws IOException{
        cartFileDAO.addPuzzle(testCarts.get(0), new Puzzle());
        assertEquals(testCarts.get(0),cartFileDAO.update(testCarts.get(0)));

    }

    @Test
    void testAddCustomPuzzle() throws IOException{
        cartFileDAO.addCustomPuzzle(testCarts.get(0), new CustomPuzzle(0, 1, "null", 1, Difficulty.EASY, "null"));
        assertEquals(1, testCarts.get(0).getCustomPuzzles().size());

    }

    @Test
    void testAddCustomPuzzleAccounID() throws IOException{
        cartFileDAO.addCustomPuzzle(testCarts.get(0).getId(), new CustomPuzzle(0, 1, "null", 1, Difficulty.EASY, "null"));
        assertEquals(1, testCarts.get(0).getCustomPuzzles().size());

    }

    @Test
    void testRemoveCustomPuzzle() throws IOException{
        CustomPuzzle puzzle = new CustomPuzzle(0, 1, "null", 1, Difficulty.EASY, "null");
        cartFileDAO.addCustomPuzzle(testCarts.get(0), puzzle);
        cartFileDAO.removeCustomPuzzle(testCarts.get(0).getId(), puzzle);
        assertEquals(0, testCarts.get(0).getCustomPuzzles().size());
        
    }

    @Test
    void testAddPuzzleTwoInts() throws IOException{
        Puzzle puzzle = new Puzzle(0, null, 0, null, 0, null, null);
        cartFileDAO.addPuzzle(testCarts.get(0).getId(), 0);
        assertEquals(1, testCarts.get(0).getPuzzles().size());
        
    }

    @Test
    void testClearCart() throws IOException{
        CustomPuzzle puzzle = new CustomPuzzle(0, 1, "null", 1, Difficulty.EASY, "null");
        cartFileDAO.addPuzzle(testCarts.get(0).getId(), 0);
        cartFileDAO.addPuzzle(testCarts.get(0).getId(), 1);
        cartFileDAO.addPuzzle(testCarts.get(0).getId(), 2);
        cartFileDAO.addCustomPuzzle(testCarts.get(0), puzzle);

        cartFileDAO.clearCart(testCarts.get(0).getId());
        assertEquals(0, testCarts.get(0).getCustomPuzzles().size());
        assertEquals(0, testCarts.get(0).getPuzzles().size());
        assertFalse(cartFileDAO.removeCustomPuzzle(testCarts.get(0).getId(),puzzle));
        assertThrows(IllegalArgumentException.class, ()  -> cartFileDAO.clearCart(100));


        
    }

    @Test
    void testGetByAccount(){
        Account account = new Account(2, "null");
        Cart cart = cartFileDAO.getByAccount(testCarts.get(0).getId());
        Cart cart2 = cartFileDAO.getByAccount(account);
        assertEquals(testCarts.get(0), cart);
        assertEquals(testCarts.get(1), cart2);
        assertNull(cartFileDAO.getByAccount(100));
        
    }
}
