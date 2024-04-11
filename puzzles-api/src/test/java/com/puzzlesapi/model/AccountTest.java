package com.puzzlesapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class AccountTest {
    @Test
    public void testCtor() {
        Object[] expectedValues = {99, "test_user"};
        int expected_id = (int) expectedValues[0];
        String expected_username = (String) expectedValues[1];

        Account account = new Account(expected_id, expected_username);
        Object[] actualValues = {account.getId(), account.getUsername()};

        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], actualValues[i]);
        }
    }

    @Test
    public void testUsername() {
        int id = 99;
        String username = "pre_test";
        Account account = new Account(id, username);

        String expected_username = "post_test";

        account.setUsername(expected_username);

        assertEquals(expected_username, account.getUsername());
    }

    @Test
    public void testToString() {
        int id = 99;
        String username = "test_user";
        String expected_string = String.format(Account.STRING_FORMAT, id, username);

        Account account = new Account(id, username);

        String actual_string = account.toString();

        assertEquals(expected_string, actual_string);
    }

    @Test
    public void testEqualsFalse(){
        int id_1 = 99;
        String username_1 = "test";
        Account account_1 = new Account(id_1, username_1);

        int id_2 = 90;
        String username_2 = "test 2";
        Account account_2 = new Account(id_2, username_2);
        
        assertFalse(account_1.equals(account_2));
    }

    @Test void testEqualsNotAccount(){
        Object[] notAccount = {99, "test_user"};
        int id = 98;
        String username = "test1";
        Account account = new Account(id, username);

        assertFalse(account.equals(notAccount));
    }

    @Test
    public void testGetHash(){
        int id = 99;
        String username = "test";
        Account account = new Account(id, username);

        int expected = 99;
        assertEquals(account.hashCode(), expected);
    }

    @Test
    public void testAdminCompare(){
        int id_admin = 01;
        String username_admin = "admin";
        Account adminAccount = new Account(id_admin, username_admin);

        int id = 99;
        String username = "test";
        Account account = new Account(id, username);
        
        int expected = 1;
        int expected2 = -1;

        assertEquals(expected, adminAccount.compareTo(account));
        assertEquals(expected2, account.compareTo(adminAccount));
    }

    @Test
    public void testCompareNoAdmin(){
        int id_1 = 99;
        String username_1 = "test";
        Account account_1 = new Account(id_1, username_1);

        int id_2 = 90;
        String username_2 = "test 2";
        Account account_2 = new Account(id_2, username_2);

        int expected = 0;
        
        assertNotEquals(expected, account_1.compareTo(account_2));
        assertEquals(expected, account_1.compareTo(account_1));
    }
}
