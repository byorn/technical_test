package com.checkout.system.rules;

public class PricingRuleConstants {

    public enum DiscountApplyTo{
        FOR_TOTAL,
        PER_ITEM ,

    }

    public enum ApplyCondition{
        EXCEEDS_THRESHOLD,
        FREE_ITEM,

    }

}
