package com.checkout.system.rules;

import com.checkout.system.ScannedItemSummary;

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

    public void applyPricingRules(Map<String, ScannedItemSummary> checkoutItems){

        pricingRuleList.forEach(pricingRule -> {

                ScannedItemSummary scannedItemSummary = checkoutItems.get(pricingRule.getItemCode());
                if(scannedItemSummary != null ){

                    if(pricingRule.getApplyCondition().equals(PricingRuleConstants.ApplyCondition.EXCEEDS_THRESHOLD)) {

                        if (itemTotalExceedsPricingRuleThreshold(scannedItemSummary,pricingRule)) {

                            if (pricingRuleDiscountToBeAppliedToTheItemTotal(pricingRule)) {

                                scannedItemSummary.reducePrice(pricingRule.getDiscount());

                            } else {

                                BigDecimal priceToReduce = BigDecimal.valueOf(pricingRule.getDiscount().doubleValue() * scannedItemSummary.getNumberOfItems());
                                scannedItemSummary.reducePrice(priceToReduce);
                            }
                        }
                    }
                    else if (pricingRule.getApplyCondition().equals(PricingRuleConstants.ApplyCondition.FREE_ITEM)) {
                        ScannedItemSummary otherBoughtItem = checkoutItems.get(pricingRule.getOtherExistingItemCode());
                        if(otherBoughtItem != null){
                            BigDecimal amountToReduce;
                            if(otherBoughtItem.getNumberOfItems() >= scannedItemSummary.getNumberOfItems()){
                                amountToReduce = BigDecimal.valueOf(scannedItemSummary.getNumberOfItems() * pricingRule.getDiscount().doubleValue());
                            }else{
                                amountToReduce = BigDecimal.valueOf(otherBoughtItem.getNumberOfItems() * pricingRule.getDiscount().doubleValue());
                            }
                            scannedItemSummary.reducePrice(amountToReduce);
                      }
                    }
                }

        });

    }


    private boolean itemTotalExceedsPricingRuleThreshold(ScannedItemSummary scannedItemSummary, PricingRule pricingRule){
        return scannedItemSummary.getPriceTotal().compareTo(pricingRule.getPriceThreshold()) >= 0;
    }

    private boolean pricingRuleDiscountToBeAppliedToTheItemTotal(PricingRule pricingRule){
        return pricingRule.getDiscountApplyTo().equals(PricingRuleConstants.DiscountApplyTo.FOR_TOTAL);
    }
}
