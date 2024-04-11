package com.puzzlesapi.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.puzzlesapi.model.Account;
@Tag("dto-tier")
public class OrderDTOTest {
    @Test
    public void testCtor() {
        // Setup
        Object[] expectedValues = {0, null, null, null,0.0};

        // Invoke
        OrderDTO order = new OrderDTO();
        Object[] actualValues = {order.getId(), order.getBuyer(), order.getItems(),order.getCustomPuzzles(),order.getTotal()};
        // Analyze
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], actualValues[i]);
        }
    }


    @Test
    public void testEqualsNotPuzzle(){
        Object[] notOrder = {98, "test_puzzle"};

        int id = 99;
        Account account = new Account(1, "user");
        double total = 50.0;

        OrderDTO order = new OrderDTO(id, account, null, null, total);

        assertFalse(order.equals(notOrder));

    }

}

