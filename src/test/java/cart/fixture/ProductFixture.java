package cart.fixture;

import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;

public class ProductFixture {
    public static class PIZZA {
        public static Product PRODUCT = new Product("PIZZA", ImageFixture.url, 10000);
        public static ProductRequest REQUEST = new ProductRequest(ImageFixture.url, "PIZZA", 1000);
        public static ProductResponse RESPONSE = new ProductResponse(1L, ImageFixture.url, "PIZZA", 1000);
    }

    public static class CHICKEN {
        public static Product PRODUCT = new Product("CHICKEN", ImageFixture.url, 10000);
        public static ProductRequest REQUEST = new ProductRequest(ImageFixture.url, "CHICKEN", 1000);
        public static ProductResponse RESPONSE = new ProductResponse(1L, ImageFixture.url, "CHICKEN", 1000);
    }

    public static class RAMYEON {
        public static Product PRODUCT = new Product("RAMYEON", ImageFixture.url, 10000);
        public static ProductRequest REQUEST = new ProductRequest(ImageFixture.url, "RAMYEON", 1000);
        public static ProductResponse RESPONSE = new ProductResponse(1L, ImageFixture.url, "RAMYEON", 1000);
    }

    public static class COFFEE {
        public static Product PRODUCT = new Product("COFFEE", ImageFixture.url, 10000);
        public static ProductRequest REQUEST = new ProductRequest(ImageFixture.url, "COFFEE", 1000);
        public static ProductResponse RESPONSE = new ProductResponse(1L, ImageFixture.url, "COFFEE", 1000);
    }

    public static class WATER {
        public static Product PRODUCT = new Product("WATER", ImageFixture.url, 500);
        public static ProductRequest REQUEST = new ProductRequest(ImageFixture.url, "WATER", 1000);
        public static ProductResponse RESPONSE = new ProductResponse(1L, ImageFixture.url, "WATER", 1000);
    }

    public static class SNACK {
        public static Product PRODUCT = new Product("SNACK", ImageFixture.url, 10000);
        public static ProductRequest REQUEST = new ProductRequest(ImageFixture.url, "SNACK", 1000);
        public static ProductResponse RESPONSE = new ProductResponse(1L, ImageFixture.url, "SNACK", 1000);
    }

    public static class COKE {
        public static Product PRODUCT = new Product("COKE", ImageFixture.url, 10000);
        public static ProductRequest REQUEST = new ProductRequest(ImageFixture.url, "COKE", 1000);
        public static ProductResponse RESPONSE = new ProductResponse(1L, ImageFixture.url, "COKE", 1000);
    }

    public static class SANDWICH {
        public static Product PRODUCT = new Product("SANDWICH", ImageFixture.url, 10000);
        public static ProductRequest REQUEST = new ProductRequest(ImageFixture.url, "SANDWICH", 1000);
        public static ProductResponse RESPONSE = new ProductResponse(1L, ImageFixture.url, "SANDWICH", 1000);
    }
}
