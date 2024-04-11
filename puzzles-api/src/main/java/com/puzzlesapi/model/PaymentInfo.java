package com.puzzlesapi.model;

public class PaymentInfo extends ModelTemplate{
    private int accountId;
    private String cardNumber;
    private int securityCode;
    private String expirationDate;
    private String name;
    final static String STRING_FORMAT = "PaymentInfo[id=%d, account_id=%d, card_number='%s', sec_code=%d, exp_date='%s', name='%s']";

    public PaymentInfo(int id, int accountId, String cardNumber, int securityCode, String expirationDate, String name) {
        super(id);
        this.accountId = accountId;
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.expirationDate = expirationDate;
        this.name = name;
    }

    public PaymentInfo() {
        this(0, 0, "1234567890", 111, "11/11", "Default");
    }

    public int getAccountId() {
        return accountId;
    }
    
    public void setAccountId(int account_id) {
        this.accountId = account_id;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String card_number) {
        this.cardNumber = card_number;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int sec_code) {
        this.securityCode = sec_code;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String exp_date) {
        this.expirationDate = exp_date;
    }

    public String getName() {
        return name; 
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof PaymentInfo){
            PaymentInfo paymentInfo = (PaymentInfo) o;
            return paymentInfo.getId() == this.getId();
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, getId(), accountId, cardNumber, securityCode, expirationDate, name);
    }


}
