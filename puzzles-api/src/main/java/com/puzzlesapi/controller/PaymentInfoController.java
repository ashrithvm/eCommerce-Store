package com.puzzlesapi.controller;

import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.PaymentInfo;
import com.puzzlesapi.persistence.PaymentInfoDAO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;



@RestController
@RequestMapping("paymentInfos")

public class PaymentInfoController {
    private final PaymentInfoDAO paymentInfoDAO;

    public PaymentInfoController(PaymentInfoDAO paymentInfoDAO) {
        this.paymentInfoDAO = paymentInfoDAO;
    }


    @GetMapping("/{accountId}")
    public ResponseEntity<ArrayList<PaymentInfo>> getAllByAccount(@PathVariable int accountId) {
        ArrayList<PaymentInfo> payment_infos = paymentInfoDAO.getAllByAccount(accountId);
        if(payment_infos != null) {
            return new ResponseEntity<>(payment_infos, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<PaymentInfo> create(@RequestBody PaymentInfo paymentInfo) {
        try {
            PaymentInfo newPaymentInfo = paymentInfoDAO.create(paymentInfo);
            if(newPaymentInfo != null) {
                return new ResponseEntity<>(newPaymentInfo, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<PaymentInfo> update(@RequestBody PaymentInfo paymentInfo) throws IOException{
            PaymentInfo updatedPaymentInfo = paymentInfoDAO.update(paymentInfo);
            return updatedPaymentInfo != null ? ResponseEntity.ok(updatedPaymentInfo) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentInfo> delete(@PathVariable int id) throws IOException{
        boolean deleted = paymentInfoDAO.delete(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
