package com.puzzlesapi.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.model.PaymentInfo;
import com.puzzlesapi.model.Account;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class PaymentInfoFileDAOTest {
    PaymentInfoFileDAO paymentInfoFileDAO;
    AccountDAO mockAccountDAO;
    PaymentInfo[] testPaymentInfos;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupPaymentInfoDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testPaymentInfos = new PaymentInfo[3];
        testPaymentInfos[0] = new PaymentInfo(0, 0, "0", 0, "0", "name0");
        testPaymentInfos[1] = new PaymentInfo(1, 0, "1", 1, "1", "name1");
        testPaymentInfos[2] = new PaymentInfo(2, 0, "2", 2, "2", "name2");
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), PaymentInfo[].class))
                .thenReturn(testPaymentInfos);
        mockAccountDAO = mock(AccountDAO.class);
        paymentInfoFileDAO = new PaymentInfoFileDAO("doesnt_matter.txt", mockObjectMapper, mockAccountDAO);
    }

    @Test
    void testCreatePaymentInfo() throws IOException {
        PaymentInfo newPaymentInfo = new PaymentInfo(1, 1, "0", 0, "0", "0");
        when(mockAccountDAO.get(newPaymentInfo.getAccountId())).thenReturn(new Account());
        PaymentInfo actual = paymentInfoFileDAO.create(newPaymentInfo);
        assertEquals(newPaymentInfo.getName(), actual.getName());
    }

    @Test
    void testNullPaymentInfo() throws IOException {
        PaymentInfo newPaymentInfo = new PaymentInfo(1, 1, "0", 0, "0", "0");
        when(mockAccountDAO.get(newPaymentInfo.getAccountId())).thenReturn(null);
        PaymentInfo actual = paymentInfoFileDAO.create(newPaymentInfo);
        assertNull(actual);
    }

    @Test
    void testCreateCopyWithId() throws IOException {
        int newId = 99;
        PaymentInfo copy = paymentInfoFileDAO.createCopyWithId(testPaymentInfos[0], newId);
        assertEquals(newId, copy.getId());
    }

    @Test
    void testGet() throws IOException {
        PaymentInfo actual = paymentInfoFileDAO.get(testPaymentInfos[0].getId());
        assertEquals(testPaymentInfos[0], actual);
    }

    @Test 
    void testNullGet() throws IOException {
        PaymentInfo testNull = paymentInfoFileDAO.get(99);
        assertNull(testNull);
    }

    @Test
    void testGetAllByAccountId() throws IOException {
        ArrayList<PaymentInfo> infos = paymentInfoFileDAO.getAllByAccount(0);
        assertEquals(testPaymentInfos.length, infos.size());
    }

    @Test
    void testGetAllByAccount() throws IOException {
        Account testAccount = new Account(0, "user");
        ArrayList<PaymentInfo> infos = paymentInfoFileDAO.getAllByAccount(testAccount);
        assertEquals(testPaymentInfos.length, infos.size());
    }
}
