package com.checkout.system;


import java.math.BigDecimal;


public class ScannedItemSummary {

    private int numberOfItems;
    private BigDecimal priceTotal;

    public ScannedItemSummary(BigDecimal price){
        priceTotal = price;
        numberOfItems = 1;
    }

    public void addPrice(BigDecimal price){
        this.numberOfItems = this.numberOfItems + 1;
        this.priceTotal = this.priceTotal.add(price);
    }

    public void reducePrice(BigDecimal price){

        this.priceTotal = this.priceTotal.subtract(price);
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }



    public BigDecimal getPriceTotal() {
        return priceTotal;
    }


}
