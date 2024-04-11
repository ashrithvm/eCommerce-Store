package com.puzzlesapi.controller;

import com.puzzlesapi.dto.CustomPuzzleDTO;
import com.puzzlesapi.mapper.CustomPuzzleMapper;
import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.CustomPuzzle;
import com.puzzlesapi.model.Difficulty;
import com.puzzlesapi.persistence.AccountDAO;
import com.puzzlesapi.persistence.CustomPuzzleDAO;
import com.puzzlesapi.persistence.CustomPuzzleFileDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Tag("Controller-tier")
public class CustomPuzzleControllerTest {
    private CustomPuzzleController customPuzzleController;
    private CustomPuzzleDAO mockPuzzleDAO;
    private CustomPuzzleMapper customPuzzleMapper;

        /**
        * Before each test, create a new PuzzleController object and inject
        * a mock Puzzle DAO
         * @throws IOException 
        */
        @BeforeEach
        public void setupPuzzleController() throws IOException {
            mockPuzzleDAO = mock(CustomPuzzleFileDAO.class);
            when(mockPuzzleDAO.get(1)).thenReturn(new CustomPuzzle(1, 2, null, 1, Difficulty.EASY, ""));

            AccountDAO mockAccountDAO = mock(AccountDAO.class);
            when(mockAccountDAO.get(2)).thenReturn(new Account(2, "null"));

            customPuzzleMapper = new CustomPuzzleMapper(mockAccountDAO);
            customPuzzleController = new CustomPuzzleController(customPuzzleMapper, mockPuzzleDAO);
        }

        @Test
        public void testCreateCustomPuzzle() throws IOException{
            Account account = new Account(2, "null");
            CustomPuzzle puzzle = new CustomPuzzle(1, 2, "test", 1, Difficulty.EASY, "");
            CustomPuzzleDTO puzzleDTO = customPuzzleMapper.toDTO(puzzle);
            when(mockPuzzleDAO.create(puzzle)).thenReturn(puzzle);

            // Invoke
            ResponseEntity<CustomPuzzleDTO> response = customPuzzleController.createCustomPuzzle(puzzleDTO);

            // Analyze
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(puzzleDTO, response.getBody());

        }

        @Test
        void testDeleteCustomPuzzle() throws IOException {
            CustomPuzzle puzzle = new CustomPuzzle(2, 2, "test", 1, Difficulty.EASY, "");
            CustomPuzzleDTO expected = customPuzzleMapper.toDTO(puzzle);
            when(mockPuzzleDAO.create(puzzle)).thenReturn(puzzle);
            when(mockPuzzleDAO.delete(2)).thenReturn(true);
            customPuzzleController.createCustomPuzzle(expected);

            // Invoke
            ResponseEntity<CustomPuzzleDTO> response = customPuzzleController.deleteCustomPuzzle(2);

            // Analyze
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNull(mockPuzzleDAO.get(2));
        }

        @Test
        void testGetCustomPuzzleTest() throws IOException {
            CustomPuzzle puzzle = new CustomPuzzle(1, 2, "test", 1, Difficulty.EASY, "");
            List<CustomPuzzleDTO> expected = List.of(customPuzzleMapper.toDTO(puzzle));
            when(mockPuzzleDAO.getByAccount(1)).thenReturn(List.of(puzzle));

            // Invoke
            ResponseEntity<List<CustomPuzzleDTO>> response = customPuzzleController.getCustomPuzzlesByAccount(1);

            // Analyze
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expected, response.getBody());
        }

        @Test 
        void testGetByAccountFail() throws IOException{
            CustomPuzzle puzzle = new CustomPuzzle(1, 2, "test", 1, Difficulty.EASY, "");
            List<CustomPuzzleDTO> expected = List.of(customPuzzleMapper.toDTO(puzzle));


            when(customPuzzleMapper.toDTO(puzzle)).thenThrow(IOException.class);

            assertEquals(HttpStatus.OK, customPuzzleController.getCustomPuzzlesByAccount(0).getStatusCode());

        }
    
    }
