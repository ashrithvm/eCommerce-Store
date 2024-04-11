package com.puzzlesapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
public class PaymentInfoTest {
    @Test
    public void testCtor() {
        Object[] expectedValues = {99, 1, "1111222233334444", 123, "11/11", "test_user"};
        int expected_id = (int) expectedValues[0];
        int expected_account_id = (int) expectedValues[1];
        String expected_card_num = (String) expectedValues[2];
        int expected_sec = (int) expectedValues[3];
        String expected_date = (String) expectedValues[4];
        String expected_name = (String) expectedValues[5];

        PaymentInfo paymentInfo = new PaymentInfo(expected_id, expected_account_id, expected_card_num, expected_sec, expected_date, expected_name);
        Object[] actualValues = {paymentInfo.getId(), paymentInfo.getAccountId(), paymentInfo.getCardNumber(), 
            paymentInfo.getSecurityCode(), paymentInfo.getExpirationDate(), paymentInfo.getName()};
        
        for(int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], actualValues[i]);
        }
    }

    @Test
    public void testAccountId() {
        PaymentInfo paymentInfo = new PaymentInfo(99, 1, "1111222233334444", 123, "11/11", "test_user");
        int expectedAccountId = 99;

        paymentInfo.setAccountId(expectedAccountId);

        assertEquals(expectedAccountId, paymentInfo.getAccountId());
    }

    @Test
    public void testCardNumber() {
        PaymentInfo paymentInfo = new PaymentInfo(99, 1, "1111222233334444", 123, "11/11", "test_user");
        String expectedCardNumber = "5555666677778888";

        paymentInfo.setCardNumber(expectedCardNumber);

        assertEquals(expectedCardNumber, paymentInfo.getCardNumber());
    }

    @Test
    public void testSecurityCode() {
        PaymentInfo paymentInfo = new PaymentInfo(99, 1, "1111222233334444", 123, "11/11", "test_user");
        int expectedSecCode = 456;

        paymentInfo.setSecurityCode(expectedSecCode);

        assertEquals(expectedSecCode, paymentInfo.getSecurityCode());
    }

    @Test
    public void testExpirationDate() {
        PaymentInfo paymentInfo = new PaymentInfo(99, 1, "1111222233334444", 123, "11/11", "test_user");
        String expectedDate = "12/12";

        paymentInfo.setExpirationDate(expectedDate);

        assertEquals(expectedDate, paymentInfo.getExpirationDate());
    }

    @Test
    void testName() {
        PaymentInfo paymentInfo = new PaymentInfo(99, 1, "1111222233334444", 123, "11/11", "test_user");
        String expectedName = "new_user";

        paymentInfo.setName(expectedName);

        assertEquals(expectedName, paymentInfo.getName());
    }

    @Test
    public void testgetHash() {
        int id = 99;
        PaymentInfo paymentInfo = new PaymentInfo(id, 1, "1111222233334444", 123, "11/11", "test_user");
        int expected = 99;

        assertEquals(expected, paymentInfo.hashCode());
    }

    @Test
    public void testEqualsFalse() {
        int id_1 = 1;
        PaymentInfo paymentInfo_1 = new PaymentInfo(id_1, 1, "1111222233334444", 123, "11/11", "test_user");

        int id_2 = 2;
        PaymentInfo paymentInfo_2 = new PaymentInfo(id_2, 1, "1111222233334444", 123, "11/11", "test_user");

        assertFalse(paymentInfo_1.equals(paymentInfo_2));
    }

    @Test
    public void testEqualsNotPaymentInfo() {
        Object[] notPaymentInfo = {99, 1, "1111222233334444", 123, "11/11", "test_user"};
        PaymentInfo paymentInfo = new PaymentInfo(99, 1, "1111222233334444", 123, "11/11", "test_user");

        assertFalse(paymentInfo.equals(notPaymentInfo));
    }

    @Test
    public void testToString() {
        int id = 99;
        int account_id = 1;
        String card_number = "1111222233334444";
        int sec_number = 123;
        String exp_date = "11/11";
        String name = "test_user";
        String expected_string = String.format(PaymentInfo.STRING_FORMAT, id, account_id, card_number, sec_number, exp_date, name);

        PaymentInfo paymentInfo = new PaymentInfo(id, account_id, card_number, sec_number, exp_date, name);

        String actual = paymentInfo.toString();

        assertEquals(expected_string, actual);
    }

    


}
