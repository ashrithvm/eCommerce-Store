package com.puzzlesapi.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.model.Puzzle;
import com.puzzlesapi.model.Difficulty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Puzzle File DAO class
 *
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class PuzzleFileDAOTest {
    PuzzleFileDAO puzzleFileDAO;
    Puzzle[] testPuzzles;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     *
     * @throws IOException
     */
    @BeforeEach
    public void setupPuzzleFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testPuzzles = new Puzzle[3];
        testPuzzles[0] = new Puzzle(99, "Wi-Fire", 3, "Description", 50.0, Difficulty.EASY, "imageURL");
        testPuzzles[1] = new Puzzle(100, "Galactic Agent", 2, "Description", 60.0, Difficulty.MEDIUM, "imageURL");
        testPuzzles[2] = new Puzzle(101, "Ice Gladiator", 5, "Description", 70.0, Difficulty.HARD, "imageURL");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the puzzle array above
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Puzzle[].class))
                .thenReturn(testPuzzles);
        puzzleFileDAO = new PuzzleFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetPuzzles() throws IOException {
        // Invoke
        ArrayList<Puzzle> puzzles = puzzleFileDAO.getAll();
        System.out.println(puzzles);

        // Analyze
        assertEquals(puzzles.size(), testPuzzles.length);
        for (int i = 0; i < testPuzzles.length; ++i)
            assertEquals(puzzles.get(i), testPuzzles[i]);
    }

    @Test
    public void testFindPuzzles() throws IOException {
        // Invoke
        ArrayList<Puzzle> puzzles = puzzleFileDAO.find("la");

        // Analyze
        assertEquals(puzzles.size(), 2);
        assertEquals(puzzles.get(0), testPuzzles[1]);
        assertEquals(puzzles.get(1), testPuzzles[2]);
    }

    @Test
    public void testGetPuzzle() throws IOException {
        // Invoke
        Puzzle puzzle = puzzleFileDAO.get(99);

        // Analyze
        assertEquals(puzzle, testPuzzles[0]);
    }

    @Test
    public void testDeletePuzzle() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> puzzleFileDAO.delete(99),
                "Unexpected exception thrown");

        // Analyze
        assertTrue(result);
        // We check the internal tree map size against the length
        // of the test puzzles array - 1 (because of the delete)
        // Because puzzles attribute of PuzzleFileDAO is package private
        // we can access it directly
        assertEquals(puzzleFileDAO.puzzles.size(), testPuzzles.length - 1);
    }

    @Test
    public void testCreatePuzzle() throws IOException {
        // Setup
        Puzzle puzzle = new Puzzle(102, "Wonder-Challenge", 4, "Description", 80.0, Difficulty.HARD, "imageURL");

        // Invoke
        Puzzle result = assertDoesNotThrow(() -> puzzleFileDAO.create(puzzle),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Puzzle actual = puzzleFileDAO.get(puzzle.getId());
        assertEquals(actual.getId(), puzzle.getId());
        assertEquals(actual.getName(), puzzle.getName());
    }

    @Test
    public void testUpdatePuzzle() throws IOException {
        // Setup
        Puzzle puzzle = new Puzzle(99, "Wi-Fire", 3, "Description", 50.0, Difficulty.EASY, "imageURL");

        // Invoke
        Puzzle result = assertDoesNotThrow(() -> puzzleFileDAO.update(puzzle),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Puzzle actual = puzzleFileDAO.get(puzzle.getId());
        assertEquals(actual, puzzle);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException())
                .when(mockObjectMapper)
                .writeValue(any(File.class), any(Collection.class));

        Puzzle puzzle = new Puzzle(102, "Wi-Fireadsdas", 3, "Description", 50.0, Difficulty.EASY, "imageURL");
        assertThrows(IOException.class,
                () -> puzzleFileDAO.create(puzzle),
                "IOException not thrown");
    }

    @Test
    public void testGetPuzzleNotFound() throws IOException {
        // Invoke
        Puzzle puzzle = puzzleFileDAO.get(98);

        // Analyze
        assertNull(puzzle);
    }

    @Test
    public void testDeletePuzzleNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> puzzleFileDAO.delete(98),
                "Unexpected exception thrown");
    }
}
