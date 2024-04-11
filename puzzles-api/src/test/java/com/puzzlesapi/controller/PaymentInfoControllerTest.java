package com.puzzlesapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.puzzlesapi.model.PaymentInfo;
import com.puzzlesapi.persistence.PaymentInfoDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-Tier")
public class PaymentInfoControllerTest {
    private PaymentInfoController paymentInfoController;
    private PaymentInfoDAO mockPaymentInfoDAO;

    @BeforeEach
    public void setupPaymentInfoController() {
        mockPaymentInfoDAO = mock(PaymentInfoDAO.class);
        paymentInfoController = new PaymentInfoController(mockPaymentInfoDAO);
    }

    @Test
    public void testGetAllByAccount() throws IOException {
        int accountId = 1;
        PaymentInfo paymentInfo1 = new PaymentInfo(0, 0, "0", 0, "0", "name0");
        PaymentInfo paymentInfo2 = new PaymentInfo(1, 0, "1", 1, "1", "name1");
        PaymentInfo paymentInfo3 = new PaymentInfo(2, 0, "2", 2, "2", "name2");
        ArrayList<PaymentInfo> paymentInfos = new ArrayList<>(List.of(paymentInfo1, paymentInfo2, paymentInfo3));
        when(mockPaymentInfoDAO.getAllByAccount(accountId)).thenReturn(paymentInfos);

        ResponseEntity<ArrayList<PaymentInfo>> response = paymentInfoController.getAllByAccount(accountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentInfos, response.getBody());
    }

    @Test
    public void testGetAllNotFound() throws IOException {
        int accountId = 1;
        when(mockPaymentInfoDAO.getAllByAccount(accountId)).thenReturn(null);

        ResponseEntity<ArrayList<PaymentInfo>> response = paymentInfoController.getAllByAccount(accountId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreate() throws IOException {
        PaymentInfo paymentInfo = new PaymentInfo(0, 0, "0", 0, "0", "name0");
        when(mockPaymentInfoDAO.create(paymentInfo)).thenReturn(paymentInfo);

        ResponseEntity<PaymentInfo> response = paymentInfoController.create(paymentInfo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(paymentInfo, response.getBody());
    }

    @Test
    public void testNullCreate() throws IOException {
        PaymentInfo paymentInfo = new PaymentInfo(0, 0, "0", 0, "0", "name0");
        when(mockPaymentInfoDAO.create(paymentInfo)).thenReturn(null);

        ResponseEntity<PaymentInfo> response = paymentInfoController.create(paymentInfo);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateHandleException() throws IOException {
        PaymentInfo paymentInfo = new PaymentInfo(0, 0, "0", 0, "0", "name0");
        doThrow(new IOException()).when(mockPaymentInfoDAO).create(paymentInfo);
        
        ResponseEntity<PaymentInfo> response = paymentInfoController.create(paymentInfo);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdate() throws IOException{
        PaymentInfo paymentInfo = new PaymentInfo(0, 0, "0", 0, "0", "name0");
        when(mockPaymentInfoDAO.update(paymentInfo)).thenReturn(paymentInfo);

        ResponseEntity<PaymentInfo> response = paymentInfoController.update(paymentInfo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentInfo, response.getBody());
    }

    @Test
    public void testNullUpdate() throws IOException {
        PaymentInfo paymentInfo = new PaymentInfo(0, 0, "0", 0, "0", "name0");
        when(mockPaymentInfoDAO.update(paymentInfo)).thenReturn(null);

        ResponseEntity<PaymentInfo> response = paymentInfoController.update(paymentInfo);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDelete() throws IOException {
        int id = 99;
        PaymentInfo paymentInfo = new PaymentInfo(id, 0, "0", 0, "0", "name0");
        when(mockPaymentInfoDAO.delete(id)).thenReturn(true);

        ResponseEntity<PaymentInfo> response = paymentInfoController.delete(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteFail() throws IOException {
        int id = 99;
        PaymentInfo paymentInfo = new PaymentInfo(id, 0, "0", 0, "0", "name0");
        when(mockPaymentInfoDAO.delete(id)).thenReturn(false);

        ResponseEntity<PaymentInfo> response = paymentInfoController.delete(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }



}
