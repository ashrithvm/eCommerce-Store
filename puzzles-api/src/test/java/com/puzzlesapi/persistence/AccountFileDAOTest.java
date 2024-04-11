package com.puzzlesapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Persistence-tier")
public class AccountFileDAOTest {
    AccountFileDAO accountFileDAO;
    Account[] testAccounts;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupAccountFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testAccounts = new Account[5];
        testAccounts[0] = new Account(1, "klejdis");
        testAccounts[1] = new Account(2, "ash");
        testAccounts[2] = new Account(3, "sejal");
        testAccounts[3] = new Account(4, "jon");
        testAccounts[4] = new Account(5, "nicholas");

        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Account[].class))
                .thenReturn(testAccounts);
        accountFileDAO = new AccountFileDAO("doesnt_matter.txt", mockObjectMapper, mock(CartDAO.class));
    }

    @Test
    public void testGetAccounts() throws IOException {
        System.out.println(accountFileDAO.getAll());
        assertEquals(5, accountFileDAO.getAll().size());
    }
    @Test
    public void testGetAccountById() throws IOException {
        Account expected = testAccounts[0];
        Account actual = accountFileDAO.get(1);
        assertEquals(expected, actual);
    }
    @Test
    public void testGetAccountByUsername() throws IOException {
        Account expected = testAccounts[0];
        Account actual = accountFileDAO.get("klejdis");
        assertEquals(expected, actual);
    }
    @Test
    public void testFindAccounts() throws IOException {
        assertNotNull(accountFileDAO.get("klejdis"));
        assertEquals(1, accountFileDAO.find("Klej").size());
    }
    @Test
    public void testCreateAccount() throws IOException {
        Account newAccount = new Account(9, "NewUser");
        Account actual = accountFileDAO.create(newAccount);
        assertEquals(newAccount.getUsername().toLowerCase(), actual.getUsername());
    }

    @Test
    public void testUpdateAccount() throws IOException {
        System.out.println(accountFileDAO.getAll());
        Account newAccount = new Account(9, "NewUser");
        Account actual = accountFileDAO.create(newAccount);
        actual.setUsername("UpdatedUser");
        Account updatedAccount = accountFileDAO.update(actual);
        assertEquals("UpdatedUser", updatedAccount.getUsername());
    }
    @Test
    public void testDeleteAccount() throws IOException {
        boolean result = accountFileDAO.delete(1);
        assertTrue(result);
    }
}
