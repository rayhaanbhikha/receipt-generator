package com.rayhaan.receipt_generator;

public class Item {
    private Double price;
    private String description;

    public Item(Double price, String description) {
        this.price = price;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }
}
