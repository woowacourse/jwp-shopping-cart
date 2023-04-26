package cart.factory;

import cart.domain.Product;

public class ProductFactory {

    public static Product createProduct() {
        return Product.from("치킨", "url", 10000);
    }

    public static Product createOtherProduct() {
        return Product.from("피자", "URL", 20000);
    }
}
