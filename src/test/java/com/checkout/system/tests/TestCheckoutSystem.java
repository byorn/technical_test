package com.checkout.system.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.checkout.system.Checkout;
import com.checkout.system.rules.PricingRule;
import com.checkout.system.rules.PricingRules;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCheckoutSystem {

    /*
    SKU	Name	            Price
    ipd	Super iPad	        $549.99
    mbp	MacBook Pro     	$1399.99
    atv	Apple TV	        $109.50
    vga	VGA adapter         $30.00
    */

    private PricingRules initPricingRules(){
        PricingRules pricingRules = new PricingRules();

        //we're going to have a 3 for 2 deal on Apple TVs. For example, if you buy 3 Apple TVs, you will pay the price of 2 only
        PricingRule pricingRuleThreeForTwo = new PricingRule();
        pricingRuleThreeForTwo.setItemCode("atv");
        pricingRuleThreeForTwo.setDiscountApply(PricingRule.DiscountApply.FOR_TOTAL);
        pricingRuleThreeForTwo.setDiscount(BigDecimal.valueOf(109.50));
        pricingRuleThreeForTwo.setPriceThreshold(BigDecimal.valueOf(109.50 * 3));
        pricingRules.addPricingRule(pricingRuleThreeForTwo);

        //the brand new Super iPad will have a bulk discounted applied, where the price will drop to $499.99 each, if someone buys more than 4
        PricingRule pricingRuleBulkDiscount = new PricingRule();
        pricingRuleBulkDiscount.setItemCode("ipd");
        pricingRuleBulkDiscount.setDiscountApply(PricingRule.DiscountApply.PER_ITEM);
        pricingRuleBulkDiscount.setDiscount(BigDecimal.valueOf(549.99-499.99));
        pricingRuleBulkDiscount.setPriceThreshold(BigDecimal.valueOf(549.99 * 4));
        pricingRules.addPricingRule(pricingRuleBulkDiscount);

        //we will bundle in a free VGA adapter free of charge with every MacBook Pro sold
        PricingRule pricingRuleFreeVGAAdapter = new PricingRule();
        pricingRuleFreeVGAAdapter.setDiscountApply(PricingRule.DiscountApply.FOR_TOTAL);
        pricingRuleFreeVGAAdapter.setDiscount(BigDecimal.valueOf(30));
        pricingRuleFreeVGAAdapter.setPriceThreshold(BigDecimal.valueOf(1399.99));


        return pricingRules;
    }


    //SKUs Scanned: atv, atv, atv, vga Total expected: $249.00
    @Test
    public void testWhenThreeATVsAndOneVGAScanned() throws Exception {


        Checkout checkout = Checkout.init(initPricingRules());

        checkout.scan("atv",new BigDecimal(109.50));
        checkout.scan("atv",new BigDecimal(109.50));
        checkout.scan("atv",new BigDecimal(109.50));
        checkout.scan("vga",new BigDecimal(30.00));


        Assert.assertEquals(new BigDecimal(249.00).setScale(2),checkout.total());
    }


    //SKUs Scanned: atv, ipd, ipd, atv, ipd, ipd, ipd Total expected: $2718.95
    @Test
    public void testWhenTwoATVsFourIPDsScanned() throws Exception {

        Checkout checkout = Checkout.init(initPricingRules());

        checkout.scan("atv",new BigDecimal(109.50));
        checkout.scan("ipd",new BigDecimal(549.99));
        checkout.scan("ipd",new BigDecimal(549.99));
        checkout.scan("atv",new BigDecimal(109.50));
        checkout.scan("ipd",new BigDecimal(549.99));
        checkout.scan("ipd",new BigDecimal(549.99));
        checkout.scan("ipd",new BigDecimal(549.99));

        Assert.assertEquals(BigDecimal.valueOf(2718.96),checkout.total());
    }

}
