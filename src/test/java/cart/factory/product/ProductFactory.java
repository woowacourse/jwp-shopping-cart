package cart.factory.product;

import cart.domain.product.Product;

public class ProductFactory {

    public static Product createProduct() {
        return Product.from(1L, "치킨", "url", 10000);
    }

    public static Product createOtherProduct() {
        return Product.from(2L, "피자", "URL", 20000);
    }
}
