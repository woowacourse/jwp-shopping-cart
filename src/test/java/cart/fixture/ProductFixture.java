package cart.fixture;

import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;

public class ProductFixture {
    public static final class FIRST_PRODUCT {
        public static final Product PRODUCT = new Product("hongo", "url1", 10000);
        public static final Product PRODUCT_WITH_ID = new Product(1L, "hongo", "url1", 10000);
        public static final ProductRequest REQUEST = new ProductRequest("hongo", "url1", 10000);
        public static final ProductResponse RESPONSE = ProductResponse.from(PRODUCT_WITH_ID);
    }

    public static final class SECOND_PRODUCT {
        public static final Product PRODUCT = new Product("abel", "url2", 10_000_000);
        public static final Product PRODUCT_WITH_ID = new Product(2L, "abel", "url2", 10_000_000);
        public static final ProductRequest REQUEST = new ProductRequest("abel", "url2", 10_000_000);
        public static final ProductResponse RESPONSE = ProductResponse.from(PRODUCT_WITH_ID);
    }
}
