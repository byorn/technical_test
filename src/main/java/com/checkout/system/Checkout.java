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
    private Map<String, ItemSummary> checkoutItems;


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

    /**
     * During Scan - Takes In ItemCode, and Price, and stores it into the internal checkoutItems
     * @param itemCode
     * @param price
     */
    public void scan(String itemCode, BigDecimal price){
        ItemSummary total = checkoutItems.get(itemCode);
        if(total == null){
            ItemSummary checkoutItemTotal = new ItemSummary(price);
            checkoutItems.put(itemCode,checkoutItemTotal);
        }else{
            total.addPrice(price);
        }
    }

    /**
     * Calculates the Final Total of the CheckoutItems, having applied the PricingRules
     * @return total
     */
    public BigDecimal total(){
        pricingRulesBO.applyPricingRules(checkoutItems);
        return calculateTotalPrice();
    }

    private BigDecimal calculateTotalPrice(){
        BigDecimal fullTotal = BigDecimal.ZERO;

        for(Map.Entry<String, ItemSummary> entry : checkoutItems.entrySet()){
            fullTotal = fullTotal.add(entry.getValue().getTotal());
        }
        fullTotal = fullTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
        return fullTotal;
    }





}
