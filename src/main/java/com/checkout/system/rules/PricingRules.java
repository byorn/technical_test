package com.checkout.system.rules;

import com.checkout.system.ItemSummary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PricingRules {

    private List<PricingRule> pricingRuleList = new ArrayList<>();


    public void addPricingRule(PricingRule pricingRule){
        pricingRuleList.add(pricingRule);
    }

    public void applyPricingRules(Map<String, ItemSummary> itemTotalMap){

        pricingRuleList.forEach(pricingRule -> {

                ItemSummary itemSummary = itemTotalMap.get(pricingRule.getItemCode());
                if(itemSummary != null ){

                    if(itemSummary.getTotal().compareTo(pricingRule.getPriceThreshold()) >= 0){
                            if(pricingRule.getDiscountApply().equals(PricingRule.DiscountApply.FOR_TOTAL)){
                                   itemSummary.reducePrice(pricingRule.getDiscount());
                            }else{
                                   BigDecimal priceToReduce = BigDecimal.valueOf(pricingRule.getDiscount().doubleValue() * itemSummary.getCount());
                                   itemSummary.reducePrice(priceToReduce);
                            }
                    }
                }

        });

    }
}
