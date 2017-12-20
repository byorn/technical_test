package com.checkout.system.rules;

import java.math.BigDecimal;

/**
 * Class is used to Define a Pricing Rule
 */
public class PricingRule {


    /** Apply Pricing Rule To This Item Code **/
    private String itemCode;

    /** The Amount to be Reduced **/
    private BigDecimal discount;

    /** Apply the discount to the Full Total of the scanned Items, or for each individual scanned item i.e.   FOR_TOTAL, PER_ITEM ,**/
    private PricingRuleConstants.DiscountApplyTo discountApplyTo;

    /** Type of 'Exceeds Condition' Pricing Rule -  EXCEEDS_THRESHOLD,  or Type of 'FREE_ITEM' Pricing Rule **/
    private PricingRuleConstants.ApplyCondition  applyCondition;

    /** Apply the pricing rule if the item Total exceeds or equals this price threshold - Requires EXCEEDS_THRESHOLD to be applied**/
    private BigDecimal priceThreshold;

    /** Provide this item free if the other items exists - Requires 'FREE_ITEM' to be applied */
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
