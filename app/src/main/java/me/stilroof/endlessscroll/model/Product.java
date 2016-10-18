package me.stilroof.endlessscroll.model;

import android.support.annotation.NonNull;

import java.math.BigDecimal;

/**
 * @author Sergei Riabov 2016
 */

public class Product {

    private Long id;

    private String name;

    private BigDecimal price;

    private int amount;

    public Product() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
