package com.puzzlesapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Logger;

@Component
public class CartFileDAO extends BasicFileDAO<Cart> implements CartDAO {
    private Logger logger = Logger.getLogger(CartFileDAO.class.getName());
    private final TreeMap<Integer, Cart>  carts = getObjects();
    private final PuzzleDAO puzzleDAO;
    public CartFileDAO(@Value("${carts.file}") String filename, ObjectMapper mapper, PuzzleDAO puzzleDAO) throws IOException {
        super(filename, Cart[].class, new TreeMap<>(), mapper);
        this.puzzleDAO = puzzleDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart create(Cart model) throws IOException {
        return super.create(model);
    }

    /**
     * Retrieves all the carts from the file
     * @return An ArrayList of {@link Cart cart} objects, may be empty.
     */
    @Override
    public ArrayList<Cart> getAll() throws IOException {
        return super.getAll();
    }

    @Override
    public Cart createCopyWithId(Cart model, int id) {
        return new Cart(id, model.getPuzzles());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart getByAccount(int accountId) {
        try {
            return super.get(accountId);
        }
        catch (IOException e){
            return null;
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Cart update(Cart model) throws IOException {
        if (model.getId() < 1) throw new IllegalArgumentException("Id must be greater than 0");
        for(PuzzleItem item : model.getPuzzles()){
            if(puzzleDAO.get(item.getId()) == null){
                throw new IllegalArgumentException("Puzzle with id " + item.getId() + " does not exist");
            }
            if(item.getQuantity() < 1) throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        return super.update(model);
    }

    @Override
    public Cart getByAccount(Account account) {
        return getByAccount(account.getId());
    }

    @Override
    public boolean addPuzzle(Cart cart, Puzzle puzzle) throws IOException {
        return addPuzzle(cart, puzzle, 1);
    }

    @Override
    public boolean addPuzzle(Cart cart, Puzzle puzzle, int quantity) throws IOException {
        if(cart == null || puzzle == null) return false;
        if(quantity < 1) throw new IllegalArgumentException("Quantity must be greater than 0");
        synchronized (carts) {
            PuzzleItem item = cart.getPuzzleCartItem(puzzle);
            if(item != null){
                if(item.getQuantity() + quantity > puzzle.getQuantity())
                    throw new IllegalArgumentException("Not enough stock to add " + quantity + " of " + puzzle.getName());
                item.setQuantity(item.getQuantity() + quantity);
            } else {
                cart.getPuzzles().add(0, new PuzzleItem(puzzle, quantity));
            }
            save();
            return true;
        }
    }

    @Override
    public boolean addPuzzle(int accountId, int puzzleId) throws IOException {
        return addPuzzle(accountId, puzzleId, 1);
    }

    @Override
    public boolean addPuzzle(int accountId, int puzzleId, int quantity) throws IOException {
        Cart cart = getByAccount(accountId);
        Puzzle puzzle = puzzleDAO.get(puzzleId);
        if(cart == null || puzzle == null) return false;
        return addPuzzle(cart, puzzle, quantity);
    }

    @Override
    public boolean removePuzzle(Cart cart, Puzzle puzzle) throws IOException {
        return removePuzzle(cart, puzzle, Integer.MAX_VALUE);
    }

    @Override
    public boolean removePuzzle(Cart cart, Puzzle puzzle, int quantity) throws IOException {
        if(cart == null || puzzle == null) return false;
        if(quantity < 1) throw new IllegalArgumentException("Quantity must be greater than 0");
        synchronized (carts) {
            PuzzleItem item = cart.getPuzzleCartItem(puzzle);
            if(item != null){
                item.setQuantity(item.getQuantity() - quantity);
                if(item.getQuantity() <= 0){
                    cart.getPuzzles().remove(item);
                }
                save();
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean removePuzzle(int accountId, int puzzleId) throws IOException {
        return removePuzzle(accountId, puzzleId, Integer.MAX_VALUE);
    }

    @Override
    public boolean removePuzzle(int accountId, int puzzleId, int quantity) throws IOException {
        Cart cart = getByAccount(accountId);
        Puzzle puzzle = puzzleDAO.get(puzzleId);
        if(cart == null || puzzle == null) return false;

        return removePuzzle(cart, puzzle, quantity);
    }

    public boolean addCustomPuzzle(Cart cart, CustomPuzzle puzzle) throws IOException {
        if(cart == null || puzzle == null) return false;
        synchronized (carts) {
            cart.getCustomPuzzles().add(puzzle);
            save();
            return true;
        }
    }
    @Override
    public boolean addCustomPuzzle(int accountId, CustomPuzzle puzzle) throws IOException {
        Cart cart = getByAccount(accountId);
        return addCustomPuzzle(cart, puzzle);
    }

    @Override
    public void clearCart(int accountId) throws IOException {
        synchronized (carts) {
            Cart cart = getByAccount(accountId);
            if (cart != null) {
                cart.getPuzzles().clear();
                cart.getCustomPuzzles().clear();
                save();
            } else
                throw new IllegalArgumentException("Cart does not exist");
        }

    }

    @Override
    public boolean removeCustomPuzzle(int accountId, CustomPuzzle puzzle) throws IOException {
        logger.info(String.format("Removing custom puzzle %s from cart %d", puzzle, accountId));
        Cart cart = getByAccount(accountId);
        if(cart == null || puzzle == null) return false;
        synchronized (carts) {
            if(cart.getCustomPuzzles().remove(puzzle)){
                save();
                return true;
            }
            return false;
        }
    }
}
