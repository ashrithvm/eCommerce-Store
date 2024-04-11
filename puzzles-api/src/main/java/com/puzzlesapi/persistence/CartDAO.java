package com.puzzlesapi.persistence;

import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.Cart;
import com.puzzlesapi.model.CustomPuzzle;
import com.puzzlesapi.model.Puzzle;

import java.io.IOException;

public interface CartDAO extends BasicDAO<Cart>{

    /**
     * Retrieves the {@linkplain Cart cart} with the given account id
     * @param accountId The id of user that has the cart to get
     * @return The cart that belongs to the passed user
     */
    Cart getByAccount(int accountId);

    /**
     * Retrieves the {@linkplain Cart cart} with the given account
     * @param account The account that has the cart to get
     * @return The cart that belongs to the passed user
     */
    Cart getByAccount(Account account);


    /**
     * Adds a puzzle to the cart. If the puzzle is already in the cart, the quantity is incremented by 1.
     * @param cart The cart to add the puzzle to
     * @param puzzle The puzzle to add
     * @return true if the puzzle was added, false if the puzzle was already in the cart
     */
    boolean addPuzzle(Cart cart, Puzzle puzzle) throws IOException;

    /**
     * Adds *quantity* puzzles to the PuzzleItem if present. If not, a new PuzzleItem is created with the given quantity.
     * @param cart The cart to add the puzzle to
     * @param puzzle The puzzle to add
     * @param quantity The quantity of puzzles to add
     * @return true if the puzzle was added, false if the puzzle was already in the cart
     */
    boolean addPuzzle(Cart cart, Puzzle puzzle, int quantity) throws IOException;

    /**
     * Adds a puzzle to the cart. If the puzzle is already in the cart, the quantity is incremented by 1.
     * @param accountId The id of the user that has the cart
     * @param puzzleId The id of the puzzle to add
     * @return true if the puzzle was added, false if the puzzle was already in the cart
     */
    boolean addPuzzle(int accountId, int puzzleId) throws IOException;

    /**
     * Removes a puzzle from the cart entirely
     * @param accountId The id of the user that has the cart
     * @param  puzzleId The id of the puzzle to remove
     * @param quantity The quantity of puzzles to remove
     * @return true if the puzzle was removed, false if the puzzle was not in the cart
     */

    boolean addPuzzle(int accountId, int puzzleId, int quantity) throws IOException;
    boolean removePuzzle(Cart cart, Puzzle puzzle) throws IOException;

    /**
     * Removes *quantity* puzzles from the PuzzleItem if present. If the quantity is reduced to 0, the PuzzleItem is removed.
     * @param cart The cart to remove the puzzle from
     * @param puzzle The puzzle to remove
     * @param quantity The quantity of puzzles to remove
     * @return true if the puzzle was removed, false if the puzzle was not in the cart
     */
    boolean removePuzzle(Cart cart, Puzzle puzzle, int quantity) throws IOException;

    /**
     * Removes a puzzle from the cart entirely
     * @param accountId The id of the user that has the cart
     * @param  puzzleId The id of the puzzle to remove
     * @return true if the puzzle was removed, false if the puzzle was not in the cart
     */
    boolean removePuzzle(int accountId, int puzzleId) throws IOException;

    /**
     * Removes *quantity* puzzles from the PuzzleItem if present. If the quantity is reduced to 0, the PuzzleItem is removed.
     * @param accountId The id of the user that has the cart
     * @param  puzzleId The id of the puzzle to remove
     * @param quantity The quantity of puzzles to remove
     * @return true if the puzzle was removed, false if the puzzle was not in the cart
     */
    boolean removePuzzle(int accountId, int puzzleId, int quantity) throws IOException;

    /**
     * Adds a custom puzzle to the cart
     * @param cart The cart to add the custom puzzle to
     * @param puzzle The custom puzzle to add
     * @return true if the custom puzzle was added, false if the custom puzzle was already in the cart
     */
    boolean addCustomPuzzle(Cart cart, CustomPuzzle puzzle) throws IOException;
    boolean addCustomPuzzle(int accountId, CustomPuzzle puzzle) throws IOException;
    boolean removeCustomPuzzle(int accountId, CustomPuzzle puzzle) throws IOException;
    ;
    void clearCart(int accountId) throws IOException;

}
