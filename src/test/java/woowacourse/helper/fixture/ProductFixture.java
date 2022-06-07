package woowacourse.helper.fixture;

import woowacourse.shoppingcart.domain.Product;

public class ProductFixture {

    public static Product createProduct(String name, int price, String imageUrl) {
        return new Product(name, price, imageUrl);
    }
}
