package com.puzzlesapi.persistence;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.CustomPuzzle;
import com.puzzlesapi.model.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomPuzzleFileDAOTest {
    private CustomPuzzleFileDAO customPuzzleFileDAO;
    private ObjectMapper mockObjectMapper;
    @BeforeEach
    void setupCustomPuzzleFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        AccountDAO mockAccountDAO = mock(AccountDAO.class);

        CartDAO mockCartDAO = mock(CartDAO.class);

        when(mockObjectMapper.readValue(new File("doesnt_matter.txt"), CustomPuzzle[].class))
                .thenReturn(new CustomPuzzle[] {
                        new CustomPuzzle(1, 2, "test", 1, Difficulty.EASY, "") });

        when(mockAccountDAO.get(2)).thenReturn(new Account(2, "null"));
        customPuzzleFileDAO = new CustomPuzzleFileDAO("doesnt_matter.txt", mockAccountDAO, mockCartDAO, mockObjectMapper);
    }

    @Test
    void testCreate() throws IOException {
        CustomPuzzle customPuzzle = new CustomPuzzle(2, 2, "test", 1, Difficulty.EASY, "");

        CustomPuzzle created = customPuzzleFileDAO.create(customPuzzle);

        assertEquals(customPuzzle, created);
    }

    @Test
    void testCreateWhenAccountDoesNotExist() {
        CustomPuzzle customPuzzle = new CustomPuzzle(2, 3, "test", 1, Difficulty.EASY, "");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> customPuzzleFileDAO.create(customPuzzle));

        assertEquals("Account does not exist", exception.getMessage());
    }
    @Test
    void testDelete() throws IOException {
        boolean deleted = customPuzzleFileDAO.delete(1);
        assertTrue(deleted);
    }

    @Test
    void testCreateCopyWithId() {
        CustomPuzzle model = new CustomPuzzle(1, 2, "test", 1, Difficulty.EASY, "");
        CustomPuzzle copy = customPuzzleFileDAO.createCopyWithId(model, 2);

        assertEquals(2, copy.getId());
        assertEquals(2, copy.getAccountID());
        assertEquals("test", copy.getName());
        assertEquals(1, copy.getQuantity());
        assertEquals(Difficulty.EASY, copy.getDifficulty());
        assertEquals("", copy.getImageURL());
    }

    @Test
    void testGetByAccount() throws IOException {
        CustomPuzzle expected = new CustomPuzzle(1, 2, "test", 1, Difficulty.EASY, "");
        assertEquals(expected, customPuzzleFileDAO.getByAccount(2).get(0));
    }

    @Test
    void testGetByAccountAccount() throws IOException {
        CustomPuzzle expected = new CustomPuzzle(1, 2, "test", 1, Difficulty.EASY, "");
        Account account = new Account(2, "null");
        assertEquals(expected, customPuzzleFileDAO.getByAccount(account).get(0));
    }

    @Test
    void testGetByAccountWhenNoAccount() throws IOException {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> customPuzzleFileDAO.getByAccount(3));
        assertEquals("Account does not exist", exception.getMessage());
    }
}