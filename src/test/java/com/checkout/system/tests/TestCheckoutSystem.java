package com.checkout.system.tests;

import com.checkout.system.Checkout;
import com.checkout.system.rules.PricingRule;
import com.checkout.system.rules.PricingRuleConstants;
import com.checkout.system.rules.PricingRulesBO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * This Test Suite, focuses on the Checkout total for the items that have been scanned.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCheckoutSystem {



    private PricingRulesBO initPricingRules(){
        PricingRulesBO pricingRules = new PricingRulesBO();

        //we're going to have a 3 for 2 deal on Apple TVs. For example, if you buy 3 Apple TVs, you will pay the price of 2 only
        PricingRule pricingRuleThreeForTwo = new PricingRule();
        pricingRuleThreeForTwo.setItemCode(ItemPrice.APPLE_TV.getCode());
        pricingRuleThreeForTwo.setApplyCondition(PricingRuleConstants.ApplyCondition.EXCEEDS_THRESHOLD);
        pricingRuleThreeForTwo.setDiscountApplyTo(PricingRuleConstants.DiscountApplyTo.FOR_TOTAL);
        pricingRuleThreeForTwo.setDiscount(ItemPrice.APPLE_TV.getPrice());
        pricingRuleThreeForTwo.setPriceThreshold(BigDecimal.valueOf(ItemPrice.APPLE_TV.getPrice().doubleValue() * 3));
        pricingRules.addPricingRule(pricingRuleThreeForTwo);

        //the brand new Super iPad will have a bulk discounted applied, where the price will drop to $499.99 each, if someone buys more than 4
        PricingRule pricingRuleBulkDiscount = new PricingRule();
        pricingRuleBulkDiscount.setItemCode(ItemPrice.SUPER_IPAD.getCode());
        pricingRuleBulkDiscount.setApplyCondition(PricingRuleConstants.ApplyCondition.EXCEEDS_THRESHOLD);
        pricingRuleBulkDiscount.setDiscountApplyTo(PricingRuleConstants.DiscountApplyTo.PER_ITEM);
        pricingRuleBulkDiscount.setDiscount(BigDecimal.valueOf(549.99-499.99));
        pricingRuleBulkDiscount.setPriceThreshold(BigDecimal.valueOf(ItemPrice.SUPER_IPAD.getPrice().doubleValue() * 4));
        pricingRules.addPricingRule(pricingRuleBulkDiscount);

        //we will bundle in a free VGA adapter free of charge with every MacBook Pro sold
        PricingRule pricingRuleFreeVGAAdapter = new PricingRule();
        pricingRuleFreeVGAAdapter.setItemCode(ItemPrice.VGA_ADAPTER.getCode());
        pricingRuleFreeVGAAdapter.setApplyCondition(PricingRuleConstants.ApplyCondition.FREE_ITEM);
        pricingRuleFreeVGAAdapter.setDiscount(ItemPrice.VGA_ADAPTER.getPrice());
        pricingRuleFreeVGAAdapter.setOtherExistingItemCode(ItemPrice.MACBOOK_PRO.getCode());
        pricingRules.addPricingRule(pricingRuleFreeVGAAdapter);

        return pricingRules;
    }


    //SKUs Scanned: atv, atv, atv, vga  Total expected: $249.00
    //Testing PricingRule - if you buy 3 Apple TVs, you will pay the price of 2 only
    @Test
    public void testWhenThreeATVsAndOneVGAScanned() {


        Checkout checkout = Checkout.init(initPricingRules());

        checkout.scan(ItemPrice.APPLE_TV.getCode(),ItemPrice.APPLE_TV.getPrice());
        checkout.scan(ItemPrice.APPLE_TV.getCode(),ItemPrice.APPLE_TV.getPrice());
        checkout.scan(ItemPrice.APPLE_TV.getCode(),ItemPrice.APPLE_TV.getPrice());
        checkout.scan(ItemPrice.VGA_ADAPTER.getCode(),ItemPrice.VGA_ADAPTER.getPrice());


        Assert.assertEquals(BigDecimal.valueOf(249.00).setScale(2),checkout.total());
    }


    //SKUs Scanned: atv, ipd, ipd, atv, ipd, ipd, ipd   Total expected: $2718.95
    //Testing PricingRule - Ipad price will drop to $499.99 each, if someone buys more than 4
    @Test
    public void testWhenTwoATVsFourIPDsScanned() {

        Checkout checkout = Checkout.init(initPricingRules());

        checkout.scan(ItemPrice.APPLE_TV.getCode(),ItemPrice.APPLE_TV.getPrice());
        checkout.scan(ItemPrice.SUPER_IPAD.getCode(),ItemPrice.SUPER_IPAD.getPrice());
        checkout.scan(ItemPrice.SUPER_IPAD.getCode(),ItemPrice.SUPER_IPAD.getPrice());
        checkout.scan(ItemPrice.APPLE_TV.getCode(),ItemPrice.APPLE_TV.getPrice());
        checkout.scan(ItemPrice.SUPER_IPAD.getCode(),ItemPrice.SUPER_IPAD.getPrice());
        checkout.scan(ItemPrice.SUPER_IPAD.getCode(),ItemPrice.SUPER_IPAD.getPrice());
        checkout.scan(ItemPrice.SUPER_IPAD.getCode(),ItemPrice.SUPER_IPAD.getPrice());

        Assert.assertEquals(BigDecimal.valueOf(2718.95),checkout.total());
    }

    //SKUs Scanned: mbp, vga    Total expected: $1399.99
    //Testing PricingRule - Bundle a Free VGA Adapter for Every MacbookPro Scanned
    //The VGA Adapter will not be charged
    @Test
    public void testWhenOneMBPAndVGAScanned()  {

        Checkout checkout = Checkout.init(initPricingRules());

        checkout.scan(ItemPrice.MACBOOK_PRO.getCode(),ItemPrice.MACBOOK_PRO.getPrice());
        checkout.scan(ItemPrice.VGA_ADAPTER.getCode(),ItemPrice.VGA_ADAPTER.getPrice());

        Assert.assertEquals(BigDecimal.valueOf(1399.99),checkout.total());
    }

    //SKUs Scanned: mbp, vga  Total expected: $1399.99
    //Testing PricingRule - Bundle a Free VGA Adapter for Every MacbookPro Scanned
    //Customer has to pay for the additional vga scanned if quantity more than MacbookPro's
    @Test
    public void testWhenOneMBPAndTWOVGAScanned()  {

        Checkout checkout = Checkout.init(initPricingRules());

        checkout.scan(ItemPrice.MACBOOK_PRO.getCode(),ItemPrice.MACBOOK_PRO.getPrice());
        checkout.scan(ItemPrice.VGA_ADAPTER.getCode(),ItemPrice.VGA_ADAPTER.getPrice());
        checkout.scan(ItemPrice.VGA_ADAPTER.getCode(),ItemPrice.VGA_ADAPTER.getPrice());


        Assert.assertEquals(BigDecimal.valueOf(1429.99),checkout.total());
    }

    //SKUs Scanned: mbp, vga  Total expected: $1399.99
    //Testing PricingRule - Bundle a Free VGA Adapter for Every MacbookPro Scanned
    //Customer does not need to pay for the vga, and an additional vga will be provided, and this does not effect the total
    @Test
    public void testWhenTwoMBPAndOneVGAScanned()  {

        Checkout checkout = Checkout.init(initPricingRules());

        checkout.scan(ItemPrice.MACBOOK_PRO.getCode(),ItemPrice.MACBOOK_PRO.getPrice());
        checkout.scan(ItemPrice.MACBOOK_PRO.getCode(),ItemPrice.MACBOOK_PRO.getPrice());
        checkout.scan(ItemPrice.VGA_ADAPTER.getCode(),ItemPrice.VGA_ADAPTER.getPrice());

        Assert.assertEquals(BigDecimal.valueOf(2799.98),checkout.total());
    }

}
