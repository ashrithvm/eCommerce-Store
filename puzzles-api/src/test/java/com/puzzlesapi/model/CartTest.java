package com.puzzlesapi.model;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CartTest {

    @Test
    public void testCtor() {
        int expected_id = 99;
        int expected_size = 0;

        Cart cart = new Cart(expected_id);

        //actual
        int actual_id = cart.getId();
        int actual_size = cart.getPuzzles().size();

        assertEquals(expected_id, actual_id);
        assertEquals(expected_size, actual_size);
    }

    @Test
    public void testContainsPuzzle() {
        int id = 1;
        String expected_name = "whatever";
        double expected_price = 99999.99;
        int expected_quantity = 4;
        Cart cart = new Cart(id);
        Puzzle puzzle = new Puzzle("whatever", expected_price, expected_quantity);


        boolean expected_contains = true;
        cart.getPuzzles().add(new PuzzleItem(puzzle, expected_quantity));

        boolean actual_contains = cart.containsPuzzle(puzzle);

        assertEquals(expected_contains, actual_contains);
    }

    @Test
    public void testGetPuzzleCartItem() {
        int id = 1;
        String expected_name = "whatever";
        double expected_price = 99999.99;
        int expected_quantity = 4;
        Cart cart = new Cart(id);
        Puzzle puzzle = new Puzzle("whatever", expected_price, expected_quantity);
        PuzzleItem expected_item = new PuzzleItem(puzzle, expected_quantity);
        cart.getPuzzles().add(expected_item);

        PuzzleItem actual_item = cart.getPuzzleCartItem(puzzle);

        assertEquals(expected_item, actual_item);
    }

    @Test
    public void testEquals() {
        int id = 99;
        Cart cart = new Cart(id, new LinkedList<>());
        Cart other_cart = new Cart(id, new LinkedList<>(List.of(new PuzzleItem(new Puzzle(), 1))));

        assertEquals(cart, other_cart);
    }

    @Test
    public void testEqualsWhenIdIsDifferent(){
        int id = 99;
        Cart cart = new Cart(id, new LinkedList<>());
        Cart other_cart = new Cart(id + 1, new LinkedList<>());

        assertNotEquals(cart, other_cart);
    }

    @Test
    public void testHashCode() {
        int id = 99;
        Cart cart = new Cart(id, new LinkedList<>());

        assertEquals(id, cart.hashCode());
    }

    @Test
    public void testToString() {
        int id = 99;
        String expected_string = String.format(Cart.STRING_FORMAT, id, new LinkedList<>(), new LinkedList<>());
        Cart cart = new Cart(id);

        String actual_string = cart.toString();

        assertEquals(expected_string, actual_string);
    }

}
