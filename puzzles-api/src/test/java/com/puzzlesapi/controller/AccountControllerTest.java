package com.puzzlesapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.puzzlesapi.model.Account;
import com.puzzlesapi.persistence.AccountDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-tier")
public class AccountControllerTest {
    private AccountController accountController;
    private AccountDAO mockAccountDAO;
    private String auth_token;
    
    
    @BeforeEach
    public void setupAccountController() {
        auth_token = "test_token";
        mockAccountDAO = mock(AccountDAO.class);
        accountController = new AccountController(mockAccountDAO, auth_token, auth_token);
    }

    @Test
    public void testvalidate() throws IOException {
        Account account = new Account(99, "test_user");
        when(mockAccountDAO.get(account.getUsername())).thenReturn(account);

        ResponseEntity<Account> response = accountController.validate(account.getUsername(), auth_token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    public void testNullvalidate() throws IOException {
        Account account = new Account(99, "test_user");
        when(mockAccountDAO.get(account.getUsername())).thenReturn(null);

        ResponseEntity<Account> response = accountController.validate(account.getUsername(), auth_token);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testNoAuthvalidate() throws IOException {
        Account account = new Account(99, "test_user");
        String invalid_token = "bad_token";

        ResponseEntity<Account> response = accountController.validate(account.getUsername(), invalid_token);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testvalidateHandleException() throws IOException {
        Account account = new Account(99, "test_user");

        doThrow(new IOException()).when(mockAccountDAO).get(account.getUsername());

        ResponseEntity<Account> response = accountController.validate(account.getUsername(), auth_token);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreate() throws IOException {
        Account account = new Account(99, "new_user");
        when(mockAccountDAO.create(account)).thenReturn(account);

        ResponseEntity<Account> response = accountController.create(account, auth_token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    public void testNullcreate() throws IOException {
        Account account = new Account(99, "new_user");
        when(mockAccountDAO.create(account)).thenReturn(null);

        ResponseEntity<Account> response = accountController.create(account, auth_token);
        
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testNoAuthcreate() throws IOException {
        Account account = new Account(99, "new_user");
        String invalid_token = "bad_token";

        ResponseEntity<Account> response = accountController.create(account, invalid_token);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testcreateHandleException() throws IOException {
        Account account = new Account(99, "new_user");

        doThrow(new IOException()).when(mockAccountDAO).create(account);

        ResponseEntity<Account> response = accountController.create(account, auth_token);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testupdate() throws IOException {
        Account account = new Account(99, "new_user");
        when(mockAccountDAO.update(account)).thenReturn(account);

        ResponseEntity<Account> response = accountController.update(account, auth_token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    public void testNullupdate() throws IOException {
        Account account = new Account(99, "new_user");
        when(mockAccountDAO.update(account)).thenReturn(null);

        ResponseEntity<Account> response = accountController.update(account, auth_token);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDelete() throws IOException {
        Account account = new Account(99, "new_user");
        when(mockAccountDAO.delete(account.getId())).thenReturn(true);

        ResponseEntity<Account> response = accountController.delete(account.getId(), auth_token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
