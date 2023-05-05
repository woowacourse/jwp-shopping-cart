package cart.fixture;

import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;

public class ProductFixture {
    public static final class FIRST_PRODUCT {
        public static final Product PRODUCT = new Product("홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 10_000_000);
        public static final Product PRODUCT_WITH_ID = new Product(1L, "홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 10_000_000);
        public static final ProductRequest REQUEST = new ProductRequest("홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 10_000_000);
        public static final ProductResponse RESPONSE = ProductResponse.from(PRODUCT_WITH_ID);
    }

    public static final class SECOND_PRODUCT {
        public static final Product PRODUCT = new Product("아벨", "https://ca.slack-edge.com/TFELTJB7V-U04LMNLQ78X-a7ef923d5391-512", 10_000_000);
        public static final Product PRODUCT_WITH_ID = new Product(2L, "아벨", "https://ca.slack-edge.com/TFELTJB7V-U04LMNLQ78X-a7ef923d5391-512", 10_000_000);
        public static final ProductRequest REQUEST = new ProductRequest("아벨", "https://ca.slack-edge.com/TFELTJB7V-U04LMNLQ78X-a7ef923d5391-512", 10_000_000);
        public static final ProductResponse RESPONSE = ProductResponse.from(PRODUCT_WITH_ID);
    }
}
