package com.puzzlesapi.persistence;

import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.CustomPuzzle;
import com.puzzlesapi.model.Puzzle;

import java.io.IOException;
import java.util.List;

public interface CustomPuzzleDAO extends BasicDAO<CustomPuzzle>{

    /**
     * Creates a new CustomPuzzle object.
     * @param accountID The id of the account that created the custom puzzle.
     * @return The created CustomPuzzle object.
     */

    List<CustomPuzzle> getByAccount(int accountID) throws IOException;

    /**
     * Creates a new CustomPuzzle object.
     * @param account The account that created the custom puzzle.
     * @return The created CustomPuzzle object.
     */
    List<CustomPuzzle> getByAccount(Account account) throws IOException;

}
