package com.puzzlesapi.persistence;

import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.PaymentInfo;

import java.util.ArrayList;


public interface PaymentInfoDAO extends BasicDAO<PaymentInfo> {
    ArrayList<PaymentInfo> getAllByAccount(Account account);

    ArrayList<PaymentInfo> getAllByAccount(int accountId);

}
