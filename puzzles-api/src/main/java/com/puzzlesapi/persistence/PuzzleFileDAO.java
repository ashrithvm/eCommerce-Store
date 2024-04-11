package com.puzzlesapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.model.Puzzle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Implements the functionality for JSON file-based persistence for Puzzles
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author SWEN Faculty
 */
@Component
public class PuzzleFileDAO extends BasicFileDAO<Puzzle> implements PuzzleDAO {
    private static final Logger LOG = Logger.getLogger(PuzzleFileDAO.class.getName());

    /**
     * The map of puzzles, where the key is the puzzle id.
     * Provides a local cache, eliminating the need to read from the file each time.
     */
    final TreeMap<Integer, Puzzle> puzzles = getObjects();

    /**
     * Creates a Puzzle File Data Access Object
     *
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     *
     * @throws IOException when file cannot be accessed or read from
     */
    public PuzzleFileDAO(@Value("${puzzles.file}") String filename, ObjectMapper objectMapper) throws IOException {
        super(filename, Puzzle[].class, new TreeMap<>(), objectMapper);
    }

    /**
     * * {@inheritDoc}
     */
    @Override
    public ArrayList<Puzzle> getAll() throws IOException {
        return super.getAll();
    }

    /**
     * Finds all {@linkplain Puzzle puzzles} that contain the given text in their name.
     * @param containsText The text to search for in the puzzle name. Case insensitive.
     * @return An ArrayList of {@link Puzzle puzzle} objects, may be empty.
     */
    @Override
    public ArrayList<Puzzle> find(String containsText) {
        synchronized(puzzles) {
            ArrayList<Puzzle> foundPuzzles = new ArrayList<>();
            for (Puzzle puzzle : puzzles.values()) {
                if (puzzle.getName().toLowerCase().contains(containsText.toLowerCase()))
                    foundPuzzles.add(puzzle);
            }
            return foundPuzzles;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Puzzle get(int id) throws IOException {
        synchronized(puzzles) {
            return super.get(id);
        }
    }

    @Override
    public Puzzle get(String name) throws IOException {
        synchronized(puzzles) {
            for (Puzzle puzzle : puzzles.values()) {
                if (puzzle.getName().equalsIgnoreCase(name))
                    return puzzle;
            }
            return null;
        }
    }

    /**
     * Creates and saves a {@linkplain Puzzle puzzle}
     * @param puzzle {@linkplain Puzzle puzzle} object to be created and saved
     * @return new {@link Puzzle puzzle} with updated id if successful, null otherwise
     * <br>
     * null if a {@link Puzzle puzzle} with the same name already exists
     */
    @Override
    public Puzzle create(Puzzle puzzle) throws IOException {
        synchronized(puzzles) {
            if (get(puzzle.getName()) != null)  // puzzle with the mentioned name already exists
                return null;

            return super.create(puzzle);
        }
    }

    /**
     * Updates and saves a {@linkplain Puzzle puzzle} if a puzzle with the mentioned id exists.
     * @param puzzle {@linkplain Puzzle puzzle} object to be updated and saved
     * @return updated {@link Puzzle puzzle} if successful, null otherwise
     */
    @Override
    public Puzzle update(Puzzle puzzle) throws IOException {
        if(puzzle == null) return null;
        else if(puzzle.getQuantity() < 0) throw new IllegalArgumentException("Quantity must be greater than or equal to 0");
        else
            return super.update(puzzle);
    }

    /**
     * Deletes a {@linkplain Puzzle puzzle} with the given id if it exists.
     * @param id The id of the {@link Puzzle puzzle} to delete
     * @return true if the {@link Puzzle puzzle} was deleted, false otherwise
     */
    @Override
    public boolean delete(int id) throws IOException {
        return super.delete(id);
    }

    /**
     * Creates a copy of the {@linkplain Puzzle puzzle} object but with the given id rather than the original id.
     * @param puzzle The puzzle object to be copied
     * @param id The id of the new puzzle object
     * @return new puzzle
     */
    @Override
    public Puzzle createCopyWithId(Puzzle puzzle, int id) {
        return new Puzzle(id, puzzle.getName(), puzzle.getQuantity(), puzzle.getDescription(), puzzle.getPrice(), puzzle.getDifficulty(), puzzle.getImageURL());
    }
}
