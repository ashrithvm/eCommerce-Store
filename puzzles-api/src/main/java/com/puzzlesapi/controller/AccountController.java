package com.puzzlesapi.controller;

import com.puzzlesapi.model.Account;
import com.puzzlesapi.persistence.AccountDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("accounts")
public class AccountController {
    private final AccountDAO accountDao;
    private final String AUTH_TOKEN, EDIT_PERMISSION_TOKEN;
    private static final HttpHeaders invalidTokenHeader;
    static{
        invalidTokenHeader = new HttpHeaders();
        invalidTokenHeader.add("reason", "Invalid token. Access denied.");
    }
    public AccountController(AccountDAO accountDao, @Value("${auth_token}") String auth_token, @Value("${edit_permission_token}") String edit_permission_token) {
        this.accountDao = accountDao;
        this.AUTH_TOKEN = auth_token;
        this.EDIT_PERMISSION_TOKEN = edit_permission_token;
    }


    /**
     * Responds to the GET request for a {@linkplain Account account} for the given username.
     * Firstly, it checks if the token is valid. A valid token is needed so not everyone can access the accounts but
     * only those with permission to use the API. If the token is valid, it retrieves the account with the given username
     * and returns it.
     * @param username The username used to locate the {@link Account account}
     * @param token The token used to check for API access permission.
     * @return
     * ResponseEntity with 403 status if the token is invalid<br>
     * ResponseEntity with {@link Account account} object and HTTP status of OK if  token is valid and username is found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if username does not exist <br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/validate")
    public ResponseEntity<Account> validate(@RequestParam String username, @RequestParam String token) throws IOException {
        if(!token.equals(AUTH_TOKEN))
            return ResponseEntity.status(401).headers(invalidTokenHeader).build();
        try {
            Account account = accountDao.get(username);
            return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
        }
        catch (IOException e){
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Responds to the POST request to create a new {@linkplain Account account}.
     * Firstly, it checks if the token is valid. A valid token is needed so not everyone can access the accounts but
     * only those with permission to use the API. If the token is valid, it creates the account and returns it.
     * @param account The {@link Account account} object to be created
     * @param token The token used to check for API access permission.
     * @return
     * ResponseEntity with 403 status if the token is invalid<br>
     * ResponseEntity with {@link Account account} object and HTTP status of OK if  token is valid and account is created<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Account> create(@RequestBody Account account, @RequestParam String token) throws IOException {
        if(!token.equals(EDIT_PERMISSION_TOKEN))
            return ResponseEntity.status(401).headers(invalidTokenHeader).build();
        try {
            Account newAccount = accountDao.create(account);
            return newAccount != null ? ResponseEntity.ok(newAccount) : ResponseEntity.status(409).build();
        }
        catch (IOException e){
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Responds to the PUT request to update an existing {@linkplain Account account}.
     * @param account The {@link Account account} object to be updated
     * @param token The token used to check for API access permission.
     * @return ResponseEntity with 401 status if the token is invalid<br>
     * ResponseEntity with {@link Account account} object and HTTP status of OK if  token is valid and account is updated<br>
     */
    @PutMapping("")
    public ResponseEntity<Account> update(@RequestBody Account account, @RequestParam String token) throws IOException {
        if(!token.equals(EDIT_PERMISSION_TOKEN))
            return ResponseEntity.status(401).headers(invalidTokenHeader).build();

        Account updatedAccount = accountDao.update(account);
        return updatedAccount != null ? ResponseEntity.ok(updatedAccount) : ResponseEntity.notFound().build();
    }

    /**
     * Responds to the DELETE request to delete an existing {@linkplain Account account}.
     * @param id The id of the {@link Account account} to be deleted
     * @param token The token used to check for API access permission.
     * @return ResponseEntity with 401 status if the token is invalid<br>
     * ResponseEntity with HTTP status of OK if token is valid and account is deleted<br>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Account> delete(@PathVariable int id, @RequestParam String token) throws IOException {
        if(!token.equals(EDIT_PERMISSION_TOKEN))
            return ResponseEntity.status(401).headers(invalidTokenHeader).build();

        boolean deleted = accountDao.delete(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
