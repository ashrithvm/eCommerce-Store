package com.puzzlesapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.PaymentInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

@Component
public class PaymentInfoFileDAO extends BasicFileDAO<PaymentInfo> implements PaymentInfoDAO{
    private final TreeMap<Integer, PaymentInfo> payment_infos = getObjects();
    private final AccountDAO accountDAO;

    public PaymentInfoFileDAO(@Value("${payment_infos.file}") String filename, ObjectMapper mapper, AccountDAO accountDAO) throws IOException {
        super(filename, PaymentInfo[].class, new TreeMap<>(), mapper);
        this.accountDAO = accountDAO;
    }

    @Override
    public PaymentInfo create(PaymentInfo model) throws IOException {
        if(accountDAO.get(model.getAccountId()) != null) {
            return super.create(model);
        }
        else {
            return null;
        }
    }

    @Override
    public ArrayList<PaymentInfo> getAll() throws IOException {
        return super.getAll();
    }

    @Override
    public PaymentInfo createCopyWithId(PaymentInfo model, int id) {
        return new PaymentInfo(id, model.getAccountId(), model.getCardNumber(), model.getSecurityCode(), model.getExpirationDate(), model.getName());
    }

    @Override
    public PaymentInfo get(int paymentInfoId) {
        try {
            return super.get(paymentInfoId);
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public ArrayList<PaymentInfo> getAllByAccount(int accountId) {
        ArrayList<PaymentInfo> all = new ArrayList<>();
        for(PaymentInfo paymentInfo : payment_infos.values()) {
            if(paymentInfo.getAccountId() == accountId) {
                all.add(paymentInfo);
            }
        }
        return all;
    }

    @Override
    public ArrayList<PaymentInfo> getAllByAccount(Account account) {
        return getAllByAccount(account.getId());
    }

    @Override
    public PaymentInfo update(PaymentInfo paymentInfo) throws IOException {
        return super.update(paymentInfo);
    }
}
