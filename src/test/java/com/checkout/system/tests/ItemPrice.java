package com.checkout.system.tests;

import java.math.BigDecimal;

public enum ItemPrice {

        SUPER_IPAD("ipd", BigDecimal.valueOf(549.99)),
        MACBOOK_PRO("mbp", BigDecimal.valueOf(1399.99)),
        APPLE_TV("atv", BigDecimal.valueOf(109.50)),
        VGA_ADAPTER("vga", BigDecimal.valueOf(30));


        private String code;
        private BigDecimal price;

        private ItemPrice(String code, BigDecimal price) {
            this.code = code;
            this.price = price;
        }

        public String getCode() {
            return code;
        }

        public BigDecimal getPrice() {
            return price;
        }
}
