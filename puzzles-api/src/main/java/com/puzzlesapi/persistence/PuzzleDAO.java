package com.puzzlesapi.persistence;

import java.io.IOException;
import java.util.ArrayList;

import com.puzzlesapi.model.Puzzle;

/**
 * Defines the interface for Puzzle object persistence
 *
 * @author SWEN Faculty
 */
public interface PuzzleDAO extends BasicDAO<Puzzle> {

    /**
     * Retrieves the {@linkplain Puzzle puzzle} with the given name
     * @param name The name of the {@link Puzzle puzzle} to get
     * @return a {@link Puzzle puzzle} object with the matching name
     * <br>
     * null if no {@link Puzzle puzzle} with a matching name is found
     */
    Puzzle get(String name) throws IOException;


}
