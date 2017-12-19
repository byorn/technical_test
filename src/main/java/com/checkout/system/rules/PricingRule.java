package com.checkout.system.rules;

import java.math.BigDecimal;

/**
 * Class is used to Define a Pricing Rule
 */
public class PricingRule {



    private String itemCode;
    private BigDecimal discount;
    private PricingRuleConstants.DiscountApplyTo discountApplyTo;
    private PricingRuleConstants.ApplyCondition  applyCondition;

    private BigDecimal priceThreshold;
    private String otherExistingItemCode;

    public PricingRuleConstants.DiscountApplyTo getDiscountApplyTo() {
        return discountApplyTo;
    }

    public void setDiscountApplyTo(PricingRuleConstants.DiscountApplyTo discountApplyTo) {
        this.discountApplyTo = discountApplyTo;
    }

    public PricingRuleConstants.ApplyCondition getApplyCondition() {
        return applyCondition;
    }

    public void setApplyCondition(PricingRuleConstants.ApplyCondition applyCondition) {
        this.applyCondition = applyCondition;
    }

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

    public String getOtherExistingItemCode() {
        return otherExistingItemCode;
    }

    public void setOtherExistingItemCode(String otherExistingItemCode) {
        this.otherExistingItemCode = otherExistingItemCode;
    }
}
