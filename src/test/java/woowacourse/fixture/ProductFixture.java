package woowacourse.fixture;

import woowacourse.shoppingcart.domain.Product;

public class ProductFixture {

    public static final Product PRODUCT_BANANA = new Product(1L, "banana", 1_000, "woowa1.com");
    public static final Product PRODUCT_APPLE = new Product(2L, "apple", 2_000, "woowa2.com");
}
