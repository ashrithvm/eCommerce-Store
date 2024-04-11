package com.puzzlesapi.controller;

import com.puzzlesapi.dto.CartDTO;
import com.puzzlesapi.dto.OrderDTO;
import com.puzzlesapi.mapper.CartMapper;
import com.puzzlesapi.mapper.CustomPuzzleMapper;
import com.puzzlesapi.mapper.OrderMapper;
import com.puzzlesapi.mapper.PuzzleItemMapper;
import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.Cart;
import com.puzzlesapi.model.Order;
import com.puzzlesapi.persistence.AccountDAO;
import com.puzzlesapi.persistence.CartDAO;
import com.puzzlesapi.persistence.OrderDAO;
import com.puzzlesapi.persistence.PuzzleDAO;

import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;
    private OrderController faultyController;

    private OrderDAO mockOrderDAO;
    private OrderDAO mockFaultyDAO;
    private OrderMapper orderMapper;
    private OrderMapper mockFaultyMap;

    @BeforeEach
    public void setupCartController() throws IOException {
        mockOrderDAO = mock(OrderDAO.class);
        mockFaultyDAO = mock(OrderDAO.class);

        AccountDAO mockAccountDAO = mock(AccountDAO.class);
        when(mockAccountDAO.get(2)).thenReturn(new Account(2, ""));
        orderMapper = new OrderMapper(
            mockAccountDAO, new PuzzleItemMapper(mock(PuzzleDAO.class)),
                new CustomPuzzleMapper(mockAccountDAO)
        );

        mockFaultyMap =  mock(OrderMapper.class);
        orderController = new OrderController(mockOrderDAO, orderMapper);
        faultyController = new OrderController(mockFaultyDAO, mockFaultyMap);
    }

    @Test
    public void testGetAll() throws IOException {
        ArrayList<Order> list = new ArrayList<>(1);
        when(mockOrderDAO.getAll()).thenReturn(list);
        when(mockFaultyDAO.getAll()).thenThrow(IOException.class);

        ResponseEntity<List<OrderDTO>> response1 = orderController.getAll();
        ResponseEntity<List<OrderDTO>> response2 = faultyController.getAll();

        assertEquals(200, response1.getStatusCodeValue());
        assertEquals(500, response2.getStatusCodeValue());
    }

    @Test
    public void testGetByBuyer() throws IOException {
        ArrayList<Order> list = new ArrayList<>(1);
        when(mockOrderDAO.getOrdersByBuyer(1)).thenReturn(list);
        when(mockOrderDAO.getOrdersByBuyer(2)).thenReturn(null);

        ResponseEntity<List<OrderDTO>> response1 = orderController.getOrdersByBuyer(1);
        //ResponseEntity<List<OrderDTO>> response2 = orderController.getOrdersByBuyer(2);
        assertEquals(200, response1.getStatusCodeValue());
        //assertEquals(404, response2.getStatusCodeValue());

    }

    @Test
    public void testCreate() throws IOException {
        Order order = new Order(1, 2, new ArrayList<>(), new ArrayList<>(), 1);
        OrderDTO orderDTO = orderMapper.toDTO(order);
        when(mockOrderDAO.create(order)).thenReturn(order);
        when(mockFaultyDAO.create(order)).thenReturn(null);

        ResponseEntity<OrderDTO> response1 = orderController.create(orderDTO);
        ResponseEntity<OrderDTO> response2 = faultyController.create(orderDTO);

        

        assertEquals(200, response1.getStatusCodeValue());
        assertEquals(500, response2.getStatusCodeValue());
        assertEquals(orderDTO, response1.getBody());
    }

    @Test
    public void testCreateExcept() throws IOException {
        Order order = new Order(1, 2, new ArrayList<>(), new ArrayList<>(), 1);
        OrderDTO orderDTO = orderMapper.toDTO(order);
        when(mockOrderDAO.create(order)).thenThrow(IOException.class);
        when(mockFaultyDAO.create(order)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<OrderDTO> response1 = orderController.create(orderDTO);
        ResponseEntity<OrderDTO> response2 = faultyController.create(orderDTO);

        

        assertEquals(500, response1.getStatusCodeValue());
        assertEquals(500, response2.getStatusCodeValue());
    }



}
