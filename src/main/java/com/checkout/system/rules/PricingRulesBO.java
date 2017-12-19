package com.checkout.system.rules;

import com.checkout.system.ItemSummary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is used as a Container that holds the pricing rules, and provides a Business interface
 * that would recalculate the totals of the checkout items.
 */
public class PricingRulesBO {

    private List<PricingRule> pricingRuleList = new ArrayList<>();


    public void addPricingRule(PricingRule pricingRule){
        pricingRuleList.add(pricingRule);
    }

    public void applyPricingRules(Map<String, ItemSummary> checkoutItems){

        pricingRuleList.forEach(pricingRule -> {

                ItemSummary itemSummary = checkoutItems.get(pricingRule.getItemCode());
                if(itemSummary != null ){
                    if(pricingRule.getApplyCondition().equals(PricingRuleConstants.ApplyCondition.EXCEEDS_THRESHOLD)) {

                        if (itemSummary.getTotal().compareTo(pricingRule.getPriceThreshold()) >= 0) {
                            if (pricingRule.getDiscountApplyTo().equals(PricingRuleConstants.DiscountApplyTo.FOR_TOTAL)) {
                                itemSummary.reducePrice(pricingRule.getDiscount());
                            } else {
                                BigDecimal priceToReduce = BigDecimal.valueOf(pricingRule.getDiscount().doubleValue() * itemSummary.getCount());
                                itemSummary.reducePrice(priceToReduce);
                            }
                        }
                    }
                    else if (pricingRule.getApplyCondition().equals(PricingRuleConstants.ApplyCondition.FREE_ITEM)) {
                        ItemSummary otherBoughtItem = checkoutItems.get(pricingRule.getOtherExistingItemCode());
                        if(otherBoughtItem != null){
                            BigDecimal amountToReduce;
                            if(otherBoughtItem.getCount() >= itemSummary.getCount()){
                                amountToReduce = BigDecimal.valueOf(itemSummary.getCount() * pricingRule.getDiscount().doubleValue());
                            }else{
                                amountToReduce = BigDecimal.valueOf(otherBoughtItem.getCount() * pricingRule.getDiscount().doubleValue());
                            }
                            itemSummary.reducePrice(amountToReduce);
                      }
                    }
                }

        });

    }
}
