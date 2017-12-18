package com.checkout.system;


import java.math.BigDecimal;

public class ItemSummary {

    private int count;
    private BigDecimal total;

    public ItemSummary(BigDecimal price){
        total = price;
        count = 1;
    }

    public void addPrice(BigDecimal price){
        this.count = this.count + 1;
        this.total = this.total.add(price);
    }

    public void reducePrice(BigDecimal price){

        this.total = this.total.subtract(price);
    }

    public int getCount() {
        return count;
    }



    public BigDecimal getTotal() {
        return total;
    }


}
