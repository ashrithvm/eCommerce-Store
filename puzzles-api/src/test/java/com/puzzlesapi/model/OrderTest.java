package com.puzzlesapi.model;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class OrderTest {

    @Test
    void testCtor() {
        int expected_id = 99;
        int expected_buyerId = 1;
        double expected_total = 0;
        Order order = new Order(expected_id, expected_buyerId, new LinkedList<>());

        int actual_id = order.getId();
        int actual_buyerId = order.getBuyerId();
        double actual_total = order.getTotal();
        assertEquals(expected_id, actual_id);
        assertEquals(expected_buyerId, actual_buyerId);
        assertEquals(expected_total, actual_total);
    }

    @Test
    void testCtor2() {
        int expected_id = 99;
        int expected_buyerId = 1;
        double expected_total = 100;
        Order order = new Order(expected_id, expected_buyerId, new LinkedList<>(), expected_total);

        int actual_id = order.getId();
        int actual_buyerId = order.getBuyerId();
        double actual_total = order.getTotal();

        assertEquals(expected_id, actual_id);
        assertEquals(expected_buyerId, actual_buyerId);
        assertEquals(expected_total, actual_total);
    }

    @Test
    void testGetBuyerId() {
        int expected_id = 99;
        int expected_buyerId = 1;
        Order order = new Order(expected_id, expected_buyerId, new LinkedList<>());

        int actual_buyerId = order.getBuyerId();
        assertEquals(expected_buyerId, actual_buyerId);
    }

    @Test
    void testGetItems() {
        int expected_id = 99;
        int expected_buyerId = 1;
        LinkedList<PuzzleItem> expected_items = new LinkedList<>();
        Order order = new Order(expected_id, expected_buyerId, expected_items);

        LinkedList<PuzzleItem> actual_items = (LinkedList<PuzzleItem>) order.getItems();
        assertEquals(expected_items, actual_items);
    }

    @Test
    void testGetTotal() {
        int expected_id = 99;
        int expected_buyerId = 1;
        LinkedList<PuzzleItem> expected_items = new LinkedList<>();
        Order order = new Order(expected_id, expected_buyerId, expected_items);

        double actual_total = order.getTotal();
        assertEquals(0, actual_total);
    }

    @Test
    void testSetTotal() {
        int expected_id = 99;
        int expected_buyerId = 1;
        LinkedList<PuzzleItem> expected_items = new LinkedList<>();
        Order order = new Order(expected_id, expected_buyerId, expected_items);

        double expected_total = 100;
        order.setTotal(expected_total);
        double actual_total = order.getTotal();
        assertEquals(expected_total, actual_total);
    }

    @Test
    void testToString() {
        int expected_id = 99;
        int expected_buyerId = 1;
        LinkedList<PuzzleItem> expected_items = new LinkedList<>();
        Order order = new Order(expected_id, expected_buyerId, expected_items);
        String expected_string = String.format(Order.STRING_FORMAT, expected_id, expected_buyerId, 0.0);

        String actual_string = order.toString();
        assertEquals(expected_string, actual_string);
    }

    @Test
    void testEquals() {
        int id = 99;
        int buyerId = 1;
        LinkedList<PuzzleItem> items = new LinkedList<>();
        Order order = new Order(id, buyerId, items);
        Order other_order = new Order(id, buyerId, items);

        assertEquals(order, other_order);
    }

    @Test
    void testEqualsWhenIdIsDifferent() {
        int id = 99;
        int buyerId = 1;
        LinkedList<PuzzleItem> items = new LinkedList<>();
        Order order = new Order(id, buyerId, items);
        Order other_order = new Order(id + 1, buyerId, items);

        assertNotEquals(order, other_order);
    }



}
