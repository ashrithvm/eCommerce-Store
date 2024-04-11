package com.puzzlesapi.controller;

import com.puzzlesapi.model.Puzzle;
import com.puzzlesapi.persistence.PuzzleDAO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for the Puzzle resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 *
 * @author SWEN Faculty
 */
@RestController
@RequestMapping("puzzles")
public class PuzzleController {
    private static final Logger LOG = Logger.getLogger(PuzzleController.class.getName());
    private static final HttpHeaders puzzleNotFoundHeader;
    static{
        puzzleNotFoundHeader = new HttpHeaders();
        puzzleNotFoundHeader.add("reason", "Non-existent puzzle");
    }
    private final PuzzleDAO puzzleDao;

    /**
     * Creates a REST API controller to respond to requests
     *
     * @param puzzleDao The {@link PuzzleDAO Puzzle Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public PuzzleController(PuzzleDAO puzzleDao) {
        this.puzzleDao = puzzleDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Puzzle puzzle} for the given id
     *
     * @param id The id used to locate the {@link Puzzle puzzle}
     *
     * @return ResponseEntity with {@link Puzzle puzzle} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Puzzle> get(@PathVariable int id) {
        LOG.info("GET /puzzles/" + id);
        try {
            Puzzle puzzle = puzzleDao.get(id);
            if (puzzle != null)
                return new ResponseEntity<>(puzzle, HttpStatus.OK);
            else
                return new ResponseEntity<>(puzzleNotFoundHeader, HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Puzzle puzzles}
     *
     * @return ResponseEntity with array of {@link Puzzle puzzle} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<ArrayList<Puzzle>> getAll() {
        LOG.info("GET /puzzles");
        try {
            ArrayList<Puzzle> puzzles = puzzleDao.getAll();
            return new ResponseEntity<>(puzzles, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Puzzle puzzles} whose name contains
     * the text in name
     *
     * @param name The name parameter which contains the text used to find the {@link Puzzle puzzles}
     *
     * @return ResponseEntity with array of {@link Puzzle puzzle} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all puzzles that contain the text "ma"
     * GET <a href="http://localhost:8080/puzzles/?name=ma">...</a>
     */
    @GetMapping("/")
    public ResponseEntity<ArrayList<Puzzle>> search(@RequestParam String name) {
        LOG.info("GET /puzzles/?name=" + name);
        ArrayList<Puzzle> puzzles = puzzleDao.find(name);
        return new ResponseEntity<>(puzzles, HttpStatus.OK);
    }

    /**
     * Creates a {@linkplain Puzzle puzzle} with the provided puzzle object
     *
     * @param puzzle - The {@link Puzzle puzzle} to create
     *
     * @return ResponseEntity with created {@link Puzzle puzzle} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Puzzle puzzle} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Puzzle> create(@RequestBody Puzzle puzzle) {
        LOG.info("POST /puzzles/ " + puzzle);
        try {
            Puzzle newPuzzle = puzzleDao.create(puzzle);
            if (newPuzzle != null)
                return new ResponseEntity<>(newPuzzle, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Puzzle puzzle} with the provided {@linkplain Puzzle puzzle} object, if it exists
     *
     * @param puzzle The {@link Puzzle puzzle} to update
     *
     * @return ResponseEntity with updated {@link Puzzle puzzle} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Puzzle> update(@RequestBody Puzzle puzzle) {
        LOG.info("PUT /puzzles/ " + puzzle);
        try {
            if(puzzleDao.get(puzzle.getId()) == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Puzzle updatedPuzzle = puzzleDao.update(puzzle);
            if (updatedPuzzle != null)
                return new ResponseEntity<>(updatedPuzzle, HttpStatus.OK);
            else
                return new ResponseEntity<>(puzzleNotFoundHeader, HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Puzzle puzzle} with the given id
     *
     * @param id The id of the {@link Puzzle puzzle} to deleted
     *
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Puzzle> delete(@PathVariable int id) {
        LOG.info("DELETE /puzzles/" + id);
        try {
            if (puzzleDao.delete(id))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(puzzleNotFoundHeader, HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
