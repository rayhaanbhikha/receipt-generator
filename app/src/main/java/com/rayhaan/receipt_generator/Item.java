package com.rayhaan.receipt_generator;

public class Item {
    private String price;
    private String description;

    public Item(String price, String description) {
        this.price = price;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public Double getRealPrice() {
        return Double.parseDouble(price.substring(1));
    }
}
