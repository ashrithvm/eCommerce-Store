package com.puzzlesapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.Cart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Logger;

@Component
public class AccountFileDAO extends BasicFileDAO<Account> implements AccountDAO{
    private static final Logger LOG = Logger.getLogger(AccountFileDAO.class.getName());
    private final TreeMap<Integer, Account> accounts = getObjects();
    private final CartDAO cartDAO;

    public AccountFileDAO(@Value("${accounts.file}") String filename, ObjectMapper mapper, CartDAO cartDAO) throws IOException {
        super(filename, Account[].class, new TreeMap<>(), mapper);
        this.cartDAO = cartDAO;
        loadCartForEveryAccount();

    }

    private void loadCartForEveryAccount() {
        try {
            synchronized (accounts){
                for (Account account : accounts.values()) {
                    if (account.getUsername().equals("admin"))
                        continue;

                    Cart cart = cartDAO.getByAccount(account.getId());
                    if (cart == null) {
                        cart = new Cart(account.getId());
                        cartDAO.create(cart);
                    }
                }
            }
        } catch (IOException e) {
            LOG.severe("Error loading carts for accounts: " + e.getMessage());
        }
    }

    /**
     * Retrieves all {@linkplain Account accounts}, including the admin.
     * @return An ArrayList of {@link Account account} objects, may be empty
     * @throws IOException if an I/O error occurs
     */
    @Override
    public ArrayList<Account> getAll() throws IOException {
        return super.getAll();
    }

    /**
     * Retrieves a {@linkplain Account account} with the given id
     * @param id The id of the {@link Account user} to get
     * @return an {@link Account account} object with the matching id
     * <br>
     * null if no {@link Account account} with a matching id is found
     * @throws IOException if an I/O error occurs
     */
    @Override
    public Account get(int id) throws IOException {
        return super.get(id);
    }

    /**
     {@inheritDoc}
     */
    @Override
    public Account get(String username) throws IOException {
        synchronized (getObjects()) {
            for (Account account : getObjects().values()) {
                if (account.getUsername().equalsIgnoreCase(username)) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * Creates and saves an {@linkplain Account account}
     * @param account {@linkplain Account account} object to be created and saved
     * @return new {@link Account account} with updated id if successful, null otherwise
     * @throws IOException if an I/O error occurs
     */
    @Override
    public Account create(Account account) throws IOException {
        if(get(account.getUsername()) != null)
            return null;

        Account accountCreated = super.create(account);
        Cart cart = new Cart(accountCreated.getId());
        cartDAO.create(cart);
        return accountCreated;
    }

    /**
     * Updates and saves an {@linkplain Account account} if an account with the mentioned id exists.
     * @param account {@linkplain Account account} object to be updated and saved
     * @return updated {@link Account account} if successful, null otherwise
     * @throws IOException if an I/O error occurs
     */
    @Override
    public Account update(Account account) throws IOException {
        return super.update(account);
    }

    /**
     * Deletes an {@linkplain Account account} with the given id if it exists.
     * @param id The id of the {@link Account user} to delete
     * @return true if the {@link Account account} was deleted, false otherwise
     * @throws IOException if an I/O error occurs
     */
    @Override
    public boolean delete(int id) throws IOException {
        boolean accountDeleted = super.delete(id);
        if(accountDeleted)
            cartDAO.delete(id);
        return accountDeleted;
    }

    /**
     * Finds all {@linkplain Account accounts} whose username contains the given text.
     * @param text The text to match against
     * @return  An ArrayList of {@link Account account} objects whose usernames contain the given text, may be empty
     */
    @Override
    public ArrayList<Account> find(String text) {
        ArrayList<Account> foundAccounts = new ArrayList<>();
        synchronized (accounts) {
            for (Account account : accounts.values()) {
                if (account.getUsername().contains(text.toLowerCase())) {
                    foundAccounts.add(account);
                }
            }
        }
        return foundAccounts;
    }

    /**
     * Creates a copy of the {@linkplain Account account} but with the given id rather than the original id.
     * @param account The {@link Account account} to be copied
     * @param id The id of the new {@link Account account}
     * @return new {@link Account account} if successful, null otherwise
     */
    @Override
    public Account createCopyWithId(Account account, int id) {
        return new Account(id, account.getUsername());
    }
}
