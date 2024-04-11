package com.puzzlesapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.model.Order;
import com.puzzlesapi.model.Puzzle;
import com.puzzlesapi.model.PuzzleItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderFIleDAOTest {
    private ObjectMapper mockObjectMapper;
    private Order[] testOrders;
    private OrderDAO orderDAO;
    private PuzzleDAO puzzleDAO;

    private void setupPuzzleDAO() throws IOException {
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Puzzle[].class))
                .thenReturn(new Puzzle[] {
                });
        puzzleDAO = new PuzzleFileDAO("doesnt_matter.txt", mockObjectMapper);
        puzzleDAO.create(new Puzzle("P1", 20.00, 3));
        puzzleDAO.create(new Puzzle("P2", 30.00, 2));
        puzzleDAO.create(new Puzzle("P3", 40.00, 1));
        puzzleDAO.create(new Puzzle("P4", 50.00, 4));
    }

    @BeforeEach
    void setUp() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        ArrayList<PuzzleItem> testPuzzleItems = new ArrayList<>(
                Arrays.asList(
                        new PuzzleItem(new Puzzle("P1", 20.00, 3), 3),
                        new PuzzleItem(new Puzzle("P2", 30.00, 2), 2),
                        new PuzzleItem(new Puzzle("P3", 40.00, 1), 1)
                )
        );
        testOrders = new Order[5];
        for (int i = 0; i < 5; i++) {
            testOrders[i] = new Order(i + 1, i + 1, testPuzzleItems);
        }

        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Order[].class))
                .thenReturn((Order[]) testOrders);

        setupPuzzleDAO();
        orderDAO = new OrderFileDAO("doesnt_matter.txt", puzzleDAO, mock(CartDAO.class), mockObjectMapper);
    }

    @Test
    void testGetOrders() throws IOException {
        assertEquals(5, orderDAO.getAll().size());
    }

    @Test
    void testGetOrderById() throws IOException {
        Order expected = testOrders[0];
        Order actual = orderDAO.get(1);
        assertEquals(expected, actual);
    }

    @Test
    void testGetOrdersByBuyerId() throws IOException {
        Order expected = testOrders[0];
        Order actual = orderDAO.getOrdersByBuyer(1).get(0);
        assertEquals(expected, actual);
    }

    @Test
    void testCreateOrder() throws IOException {
        System.out.println(puzzleDAO.getAll());
        Order order = new Order(6, 6, new ArrayList<>(Arrays.asList(
                new PuzzleItem(puzzleDAO.get(1), 1),
                new PuzzleItem(puzzleDAO.get(2), 2)
        )));
        orderDAO.create(order);
        assertEquals(6, orderDAO.getAll().size());
    }

    @Test
    void testCreateOrderWithNonExistingPuzzle() throws IOException {
        Order order = new Order(6, 6, new ArrayList<>(Arrays.asList(
                new PuzzleItem(new Puzzle("P5", 60.00, 5), 1),
                new PuzzleItem(puzzleDAO.get(2), 2)
        )));
        assertThrows(IllegalArgumentException.class, () -> orderDAO.create(order));
    }

    @Test
    void testDeleteOrder() throws IOException {
        boolean deleted = orderDAO.delete(1);
        assertEquals(4, orderDAO.getAll().size());
        assertNull(orderDAO.get(1));
    }
}
