package com.checkout.system;


import com.checkout.system.rules.PricingRules;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Checkout {

    private PricingRules pricingRules;
    private Map<String, ItemSummary> itemTotalsMap;


    private Checkout(){
        // Intialize always with Pricing Rules
    }

    private Checkout(PricingRules pricingRules) {
        this.pricingRules = pricingRules;
        itemTotalsMap = new HashMap<>();

    }


    public static Checkout init(PricingRules pricingRules){
        Checkout checkout = new Checkout(pricingRules);
        return checkout;
    }

    public void scan(String itemCode, BigDecimal price){
        ItemSummary total = itemTotalsMap.get(itemCode);
        if(total == null){
            ItemSummary checkoutItemTotal = new ItemSummary(price);
            itemTotalsMap.put(itemCode,checkoutItemTotal);
        }else{
            total.addPrice(price);
        }
    }

    public BigDecimal total(){
        pricingRules.applyPricingRules(itemTotalsMap);
        return calculateTotalPrice();
    }

    private BigDecimal calculateTotalPrice(){
        BigDecimal fullTotal = new BigDecimal(0.00);

        for(Map.Entry<String, ItemSummary> entry : itemTotalsMap.entrySet()){
            fullTotal = fullTotal.add(entry.getValue().getTotal());
        }
        fullTotal = fullTotal.setScale(2,BigDecimal.ROUND_CEILING);
        return fullTotal;
    }





}
