package com.puzzlesapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.CustomPuzzle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

@Component
public class CustomPuzzleFileDAO extends BasicFileDAO<CustomPuzzle> implements CustomPuzzleDAO {

    private final AccountDAO accountDAO;
    private final CartDAO cartDAO;
    public CustomPuzzleFileDAO(@Value("${custom_puzzles.file}") String filename, AccountDAO accountDAO, CartDAO cartDAO, ObjectMapper objectMapper) throws IOException {
        super(filename, CustomPuzzle[].class, new TreeMap<>(), objectMapper);
        this.accountDAO = accountDAO;
        this.cartDAO = cartDAO;
    }

    @Override
    public CustomPuzzle create(CustomPuzzle customPuzzle) throws IOException {
        if(customPuzzle.getAccountID() == 1)
            throw new IllegalArgumentException("Admin cannot create custom puzzles");
        Account account = accountDAO.get(customPuzzle.getAccountID());
        if(account == null)
            throw new IllegalArgumentException("Account does not exist");
        CustomPuzzle custom = super.create(customPuzzle);
        boolean addedToCart = cartDAO.addCustomPuzzle(account.getId(), custom);
        if(addedToCart)
            System.out.printf("Added to cart: %s", custom);
        return custom;
    }

    @Override
    public boolean delete(int id) throws IOException {
        System.out.println("DELETE" + id);
        CustomPuzzle customPuzzle = get(id);
        Account account = accountDAO.get(customPuzzle.getAccountID());
        if(account == null || account.getId() == 1 || customPuzzle == null)
            return false;

        boolean removedFromCart = cartDAO.removeCustomPuzzle(account.getId(), customPuzzle);
        if(removedFromCart)
            System.out.println("Removed from cart");
        return super.delete(id);
    }
    @Override
    public CustomPuzzle createCopyWithId(CustomPuzzle model, int id) {
        return new CustomPuzzle(id, model.getAccountID(), model.getName(), model.getQuantity(), model.getDifficulty(), model.getImageURL());
    }

    @Override
    public List<CustomPuzzle> getByAccount(int accountID) throws IOException {
        if(accountDAO.get(accountID) == null)
            throw new IllegalArgumentException("Account does not exist");
        return getAll().stream().filter(puzzle -> puzzle.getAccountID() == accountID).toList();
    }

    @Override
    public List<CustomPuzzle> getByAccount(Account account) throws IOException {
        return getByAccount(account.getId());
    }
}
