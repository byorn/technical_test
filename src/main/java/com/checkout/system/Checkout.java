package com.checkout.system;


import com.checkout.system.rules.PricingRulesBO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is the implementation of the Checkout Interface
 */
public class Checkout {

    private PricingRulesBO pricingRulesBO;
    private Map<String, ScannedItemSummary> checkoutItems;


    private Checkout(){
        // Enforce - Intialize always with Pricing Rules
    }

    private Checkout(PricingRulesBO pricingRulesBO) {
        this.pricingRulesBO = pricingRulesBO;
        checkoutItems = new HashMap<>();

    }


    public static Checkout init(PricingRulesBO pricingRules){
        Checkout checkout = new Checkout(pricingRules);
        return checkout;
    }


    public void scan(String itemCode, BigDecimal price){
        ScannedItemSummary total = checkoutItems.get(itemCode);
        if(total == null){
            ScannedItemSummary checkoutItemTotal = new ScannedItemSummary(price);
            checkoutItems.put(itemCode,checkoutItemTotal);
        }else{
            total.addPrice(price);
        }
    }


    public BigDecimal total(){
        pricingRulesBO.applyPricingRules(checkoutItems);
        return calculateTotalPrice();
    }

    private BigDecimal calculateTotalPrice(){
        BigDecimal fullTotal = BigDecimal.ZERO;

        for(Map.Entry<String, ScannedItemSummary> entry : checkoutItems.entrySet()){
            fullTotal = fullTotal.add(entry.getValue().getPriceTotal());
        }
        fullTotal = fullTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
        return fullTotal;
    }





}
