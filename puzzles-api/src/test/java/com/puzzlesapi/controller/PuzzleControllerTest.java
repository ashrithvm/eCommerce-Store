package com.puzzlesapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.puzzlesapi.model.Difficulty;
import com.puzzlesapi.model.Puzzle;
import com.puzzlesapi.persistence.PuzzleDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-tier")
public class PuzzleControllerTest {
    private PuzzleController puzzleController;
    private PuzzleDAO mockPuzzleDAO;

    /**
     * Before each test, create a new PuzzleController object and inject
     * a mock Puzzle DAO
     */
    @BeforeEach
    public void setupPuzzleController() {
        mockPuzzleDAO = mock(PuzzleDAO.class);
        puzzleController = new PuzzleController(mockPuzzleDAO);
    }

    @Test
    public void testGetAllPuzzles() throws IOException {  // getAllPuzzles may throw IOException
        // Setup
        Puzzle puzzle1 = new Puzzle(99, "Galactic Agent", 3, "Description", 50.0, Difficulty.MEDIUM, "imageURL");
        Puzzle puzzle2 = new Puzzle(100, "Wi-Fire", 2, "Description", 50.0, Difficulty.EASY, "imageURL");
        Puzzle puzzle3 = new Puzzle(101, "Bolt", 4, "Description", 50.0, Difficulty.HARD, "imageURL");
        ArrayList<Puzzle> puzzles = new ArrayList<>(List.of(puzzle1, puzzle2, puzzle3));
        // When getAll is called on the Mock Puzzle DAO, return the array of puzzles
        when(mockPuzzleDAO.getAll()).thenReturn(puzzles);

        // Invoke
        ResponseEntity<ArrayList<Puzzle>> response = puzzleController.getAll();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(puzzles, response.getBody());
    }
    @Test
    public void testGetPuzzle() throws IOException {  // getPuzzle may throw IOException
        // Setup
        Puzzle puzzle = new Puzzle(99, "Galactic Agent", 3, "Description", 50.0, Difficulty.MEDIUM, "imageURL");
        // When the same id is passed in, our mock Puzzle DAO will return the Puzzle object
        when(mockPuzzleDAO.get(puzzle.getId())).thenReturn(puzzle);

        // Invoke
        ResponseEntity<Puzzle> response = puzzleController.get(puzzle.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(puzzle, response.getBody());
    }

    @Test
    public void testGetPuzzleNotFound() throws Exception { // createPuzzle may throw IOException
        // Setup
        int puzzleId = 99;
        // When the same id is passed in, our mock Puzzle DAO will return null, simulating
        // no puzzle found
        when(mockPuzzleDAO.get(puzzleId)).thenReturn(null);

        // Invoke
        ResponseEntity<Puzzle> response = puzzleController.get(puzzleId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetPuzzleHandleException() throws Exception { // createPuzzle may throw IOException
        // Setup
        int puzzleId = 99;
        // When getPuzzle is called on the Mock Puzzle DAO, throw an IOException
        doThrow(new IOException()).when(mockPuzzleDAO).get(puzzleId);

        // Invoke
        ResponseEntity<Puzzle> response = puzzleController.get(puzzleId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all PuzzleController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreatePuzzle() throws IOException {  // createPuzzle may throw IOException
        // Setup
        Puzzle puzzle = new Puzzle(99, "Wi-Fire", 2, "Description", 50.0, Difficulty.EASY, "imageURL");
        // when createPuzzle is called, return true simulating successful
        // creation and save
        when(mockPuzzleDAO.create(puzzle)).thenReturn(puzzle);

        // Invoke
        ResponseEntity<Puzzle> response = puzzleController.create(puzzle);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(puzzle, response.getBody());
    }

    @Test
    public void testCreatePuzzleFailed() throws IOException {  // createPuzzle may throw IOException
        // Setup
        Puzzle puzzle = new Puzzle(99, "Bolt", 4, "Description", 50.0, Difficulty.HARD, "imageURL");
        // when createPuzzle is called, return false simulating failed
        // creation and save
        when(mockPuzzleDAO.create(puzzle)).thenReturn(null);

        // Invoke
        ResponseEntity<Puzzle> response = puzzleController.create(puzzle);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreatePuzzleHandleException() throws IOException {  // createPuzzle may throw IOException
        // Setup
        Puzzle puzzle = new Puzzle(99, "Ice Gladiator", 1, "Description", 50.0, Difficulty.MEDIUM, "imageURL");

        // When createPuzzle is called on the Mock Puzzle DAO, throw an IOException
        doThrow(new IOException()).when(mockPuzzleDAO).create(puzzle);

        // Invoke
        ResponseEntity<Puzzle> response = puzzleController.create(puzzle);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdatePuzzle() throws IOException { // updatePuzzle may throw IOException
        // Setup
        Puzzle puzzle = new Puzzle(99, "Wi-Fire", 3, "Description", 50.0, Difficulty.EASY, "imageURL");
        // when updatePuzzle is called, return true simulating successful
        // update and save
        when(mockPuzzleDAO.update(puzzle)).thenReturn(puzzle);
        when(mockPuzzleDAO.get(puzzle.getId())).thenReturn(puzzle);
        puzzle.setName("Bolt");

        // Invoke
        ResponseEntity<Puzzle> response = puzzleController.update(puzzle);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(puzzle, response.getBody());
    }

    @Test
    public void testUpdatePuzzleFailed() throws IOException { // updatePuzzle may throw IOException
        // Setup
        Puzzle puzzle = new Puzzle(99, "Galactic Agent", 2, "Description", 50.0, Difficulty.HARD, "imageURL");
        // when updatePuzzle is called, return true simulating successful
        // update and save
        when(mockPuzzleDAO.update(puzzle)).thenReturn(null);
        when(mockPuzzleDAO.get(puzzle.getId())).thenReturn(puzzle);

        // Invoke
        ResponseEntity<Puzzle> response = puzzleController.update(puzzle);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdatePuzzleHandleException() throws IOException { // updatePuzzle may throw IOException
        // Setup
        Puzzle puzzle = new Puzzle(99, "Galactic Agent", 3, "Description", 50.0, Difficulty.MEDIUM, "imageURL");
        // When updatePuzzle is called on the Mock Puzzle DAO, throw an IOException
        doThrow(new IOException()).when(mockPuzzleDAO).update(puzzle);
        when(mockPuzzleDAO.get(puzzle.getId())).thenReturn(puzzle);

        // Invoke
        ResponseEntity<Puzzle> response = puzzleController.update(puzzle);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeletePuzzle() throws IOException{
        // Setup
        // when delete is called, return true simulating successful
        when(mockPuzzleDAO.delete(99)).thenReturn(true);
        

        // Invoke
        ResponseEntity<Puzzle> response = puzzleController.delete(99);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

    }

    @Test
    public void testDeletePuzzleFail() throws IOException{
        // when updatePuzzle is called, return false simulating no puzzle found to delete
        when(mockPuzzleDAO.delete(98)).thenReturn(false);
        

        // Invoke
        ResponseEntity<Puzzle> response = puzzleController.delete(98);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        
    }
    @Test
    public void testDeletePuzzleException() throws IOException{
        //Throw an exception for an update
        doThrow(new IOException()).when(mockPuzzleDAO).delete(99);

        //Invoke
        ResponseEntity<Puzzle> response = puzzleController.delete(99);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testPuzzleSearch() throws IOException{
        // Setup
        Puzzle puzzle = new Puzzle(99, "Galactic Agent", 3, "Description", 50.0, Difficulty.MEDIUM, "imageURL");
        ArrayList<Puzzle> search_result = new ArrayList<Puzzle>();
        search_result.add(puzzle);
        // When the same id is passed in, our mock Puzzle DAO will return the Puzzle object
        when(mockPuzzleDAO.find(puzzle.getName())).thenReturn(search_result);

        // Invoke
        ResponseEntity<ArrayList<Puzzle>> response = puzzleController.search(puzzle.getName());
 
        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(search_result, response.getBody());


    }
}
