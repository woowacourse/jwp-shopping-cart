package cart.fixture;

import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;

public class ProductFixture {
    public static Product PIZZA = new Product("PIZZA", ImageFixture.url, 10000);
    public static Product CHICKEN = new Product("CHICKEN", ImageFixture.url, 10000);
    public static Product RAMYEON = new Product("RAMYEON", ImageFixture.url, 10000);
    public static Product SANDWICH = new Product("SANDWICH", ImageFixture.url, 10000);
    public static Product COFFEE = new Product("COFFEE", ImageFixture.url, 10000);
    public static Product COKE = new Product("COKE", ImageFixture.url, 10000);
    public static Product WATER = new Product("WATER", ImageFixture.url, 500);
    public static Product SNACK = new Product("SNACK", ImageFixture.url, 10000);

    public static ProductRequest PIZZA_REQUEST = new ProductRequest(ImageFixture.url, "PIZZA", 1000);
    public static ProductRequest CHICKEN_REQUEST = new ProductRequest(ImageFixture.url, "CHICKEN", 1000);
    public static ProductRequest RAMYEON_REQUEST = new ProductRequest(ImageFixture.url, "RAMYEON", 1000);
    public static ProductRequest SANDWICH_REQUEST = new ProductRequest(ImageFixture.url, "SANDWICH", 1000);
    public static ProductRequest COFFEE_REQUEST = new ProductRequest(ImageFixture.url, "COFFEE", 1000);
    public static ProductRequest COKE_REQUEST = new ProductRequest(ImageFixture.url, "COKE", 1000);
    public static ProductRequest WATER_REQUEST = new ProductRequest(ImageFixture.url, "WATER", 1000);
    public static ProductRequest SNACK_REQUEST = new ProductRequest(ImageFixture.url, "SNACK", 1000);

    public static ProductResponse PIZZA_RESPONSE = new ProductResponse(1L, ImageFixture.url, "PIZZA", 1000);
    public static ProductResponse CHICKEN_RESPONSE = new ProductResponse(1L, ImageFixture.url, "CHICKEN", 1000);
    public static ProductResponse RAMYEON_RESPONSE = new ProductResponse(1L, ImageFixture.url, "RAMYEON", 1000);
    public static ProductResponse SANDWICH_RESPONSE = new ProductResponse(1L, ImageFixture.url, "SANDWICH", 1000);
    public static ProductResponse COFFEE_RESPONSE = new ProductResponse(1L, ImageFixture.url, "COFFEE", 1000);
    public static ProductResponse COKE_RESPONSE = new ProductResponse(1L, ImageFixture.url, "COKE", 1000);
    public static ProductResponse WATER_RESPONSE = new ProductResponse(1L, ImageFixture.url, "WATER", 1000);
    public static ProductResponse SNACK_RESPONSE = new ProductResponse(1L, ImageFixture.url, "SNACK", 1000);
}
