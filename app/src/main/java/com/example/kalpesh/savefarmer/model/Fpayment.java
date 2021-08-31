package com.example.kalpesh.savefarmer.model;

public class Fpayment {
    private String fw_id;
    private String farmer_id;
    private String Amount;

    public String getFw_id() {
        return fw_id;
    }

    public void setFw_id(String fw_id) {
        this.fw_id = fw_id;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
