package woowacourse.helper.fixture;

import woowacourse.shoppingcart.domain.Product;

public class ProductFixture {

    public static final String PRODUCT_NAME = "김치";
    public static final int PRODUCT_PRICE = 1000;
    public static final String PRODUCT_IMAGE = "product.com";

    public static final Product createProduct(final String name, final int price, final String url) {
        return new Product(name, price, url);
    }

}
