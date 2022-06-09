package woowacourse.helper.fixture;

import woowacourse.shoppingcart.domain.Product;

public class ProductFixture {

    public static final String NAME = "치킨";
    public static final int PRICE = 10_000;
    public static final String IMAGE = "http://example.com/chicken.jpg";

    public static Product createProduct(String name, int price, String imageUrl) {
        return new Product(name, price, imageUrl);
    }

    public static Product createProductWithId(Long id, String name, int price, String imageUrl) {
        return new Product(id, name, price, imageUrl);
    }
}
