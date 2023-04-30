package cart.fixture;

import cart.domain.Product;

public class ProductFixture {

    public static final Product PRODUCT_A = new Product("마우스", 10000, "image");
    public static final Product PRODUCT_B = new Product("키보드", 20000, "image2");
    public static final Product PRODUCT_A_HAS_ID = new Product(1L, "마우스", 10000, "image");
    public static final Product PRODUCT_B_HAS_ID = new Product(2L, "키보드", 20000, "image2");
}
