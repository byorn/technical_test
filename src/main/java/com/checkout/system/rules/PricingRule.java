package com.checkout.system.rules;

import java.math.BigDecimal;

public class PricingRule {

    public enum DiscountApply{
        FOR_TOTAL,PER_ITEM
    }
    private String itemCode;
    private BigDecimal discount;
    private DiscountApply discountApply;

    public DiscountApply getDiscountApply() {
        return discountApply;
    }

    public void setDiscountApply(DiscountApply discountApply) {
        this.discountApply = discountApply;
    }

    private BigDecimal priceThreshold;


    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPriceThreshold() {
        return priceThreshold;
    }

    public void setPriceThreshold(BigDecimal priceThreshold) {
        this.priceThreshold = priceThreshold;
    }
}
