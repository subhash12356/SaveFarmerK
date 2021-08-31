package com.example.kalpesh.savefarmer.model;

public class Product {

    private String Product_id;
    private String Product_Name;
    private String Product_Quantity;
    private String rate;
    private String Descri;
    private String Cate_Id;
    private String image;


    public String getProduct_Id() {
        return Product_id;
    }

    public void setProduct_Id(String product_Id) {
        Product_id = product_Id;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getProduct_Quantity() {
        return Product_Quantity;
    }

    public void setProduct_Quantity(String product_Quantity) {
        Product_Quantity = product_Quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescri() {
        return Descri;
    }

    public void setDescri(String descri) {
        Descri = descri;
    }

    public String getCate_Id() {
        return Cate_Id;
    }

    public void setCate_Id(String cate_Id) {
        Cate_Id = cate_Id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

