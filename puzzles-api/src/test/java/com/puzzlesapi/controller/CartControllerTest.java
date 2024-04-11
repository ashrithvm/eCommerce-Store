package com.puzzlesapi.controller;

import com.puzzlesapi.dto.CartDTO;
import com.puzzlesapi.mapper.CartMapper;
import com.puzzlesapi.mapper.CustomPuzzleMapper;
import com.puzzlesapi.mapper.PuzzleItemMapper;
import com.puzzlesapi.model.Cart;
import com.puzzlesapi.persistence.AccountDAO;
import com.puzzlesapi.persistence.CartDAO;
import com.puzzlesapi.persistence.PuzzleDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;
    private CartDAO mockCartDAO;
    private CartMapper cartMapper;

    @BeforeEach
    public void setupCartController() {
        mockCartDAO = mock(CartDAO.class);

        cartMapper = new CartMapper(
                new PuzzleItemMapper(mock(PuzzleDAO.class)),
                new CustomPuzzleMapper(mock(AccountDAO.class))
        );
        cartController = new CartController(mockCartDAO, cartMapper);
    }

    @Test
    public void testGetCart() {
        when(mockCartDAO.getByAccount(1)).thenReturn(new Cart(1));
        when(mockCartDAO.getByAccount(2)).thenReturn(null);

        ResponseEntity<CartDTO> response1 = cartController.getCart(1);
        ResponseEntity<CartDTO> response2 = cartController.getCart(2);
        assertEquals(200, response1.getStatusCodeValue());
        assertEquals(404, response2.getStatusCodeValue());

    }

    @Test
    public void testAddPuzzle() throws IOException {
        when(mockCartDAO.addPuzzle(1, 1)).thenReturn(true);
        when(mockCartDAO.addPuzzle(2, 2)).thenReturn(false);
        when(mockCartDAO.addPuzzle(3, 3)).thenReturn(true);
        when(mockCartDAO.addPuzzle(4, 4)).thenThrow(new IOException());

        ResponseEntity<Cart> response1 = cartController.addPuzzle(1, 1, Optional.empty());
        ResponseEntity<Cart> response2 = cartController.addPuzzle(2, 2, Optional.empty());
        //ResponseEntity<Cart> response3 = cartController.addPuzzle(3, 3, Optional.of(2));
        ResponseEntity<Cart> response4 = cartController.addPuzzle(4, 4, Optional.empty());
        assertEquals(200, response1.getStatusCodeValue());
        assertEquals(404, response2.getStatusCodeValue());
        assertEquals(500, response4.getStatusCodeValue());
        //assertEquals(200, response3.getStatusCodeValue());
    }

    @Test
    public void testRemovePuzzle() throws IOException {
        when(mockCartDAO.removePuzzle(1, 1)).thenReturn(true);
        when(mockCartDAO.removePuzzle(2, 2)).thenReturn(false);
        when(mockCartDAO.removePuzzle(3, 3)).thenThrow(new IOException());

        ResponseEntity<Cart> response1 = cartController.removePuzzle(1, 1);
        ResponseEntity<Cart> response2 = cartController.removePuzzle(2, 2);
        ResponseEntity<Cart> response3 = cartController.removePuzzle(3, 3);
        assertEquals(200, response1.getStatusCodeValue());
        assertEquals(404, response2.getStatusCodeValue());
        assertEquals(500, response3.getStatusCodeValue());
    }

    @Test
    public void testUpdateCart() throws IOException{
        Cart cart = new Cart(1);
        Cart except = new Cart(0);

        when(mockCartDAO.update(cart)).thenReturn(cart);
        when(mockCartDAO.update(except)).thenThrow(new IOException());


        ResponseEntity<CartDTO> response = cartController.updateCart(1, cartMapper.toDTO(cart));
        ResponseEntity<CartDTO> exception = cartController.updateCart(0, cartMapper.toDTO(except));
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(500, exception.getStatusCodeValue());

    }


}
