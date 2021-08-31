package com.example.kalpesh.savefarmer.model;

public class Wpayment {
    private String Payment_Type_id;
    private String Payee_Id;
    private String Amount;

    public String getPayment_Type_id() {
        return Payment_Type_id;
    }

    public void setPayment_Type_id(String payment_Type_id) {
        Payment_Type_id = payment_Type_id;
    }

    public String getPayee_Id() {
        return Payee_Id;
    }

    public void setPayee_Id(String payee_Id) {
        Payee_Id = payee_Id;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
