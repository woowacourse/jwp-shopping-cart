package cart.fixture;

import cart.service.product.Product;
import cart.service.product.dto.ProductRequest;
import cart.service.product.dto.ProductResponse;

public class ProductFixture {
    public static Product PIZZA = new Product("PIZZA", "url", 10000);
    public static Product CHICKEN = new Product("CHICKEN", "url", 10000);
    public static Product RAMYEON = new Product("RAMYEON", "url", 10000);
    public static Product SANDWICH = new Product("SANDWICH", "url", 10000);
    public static Product COFFEE = new Product("COFFEE", "url", 10000);
    public static Product COKE = new Product("COKE", "url", 10000);
    public static Product WATER = new Product("WATER", "url", 10000);
    public static Product SNACK = new Product("SNACK", "url", 10000);

    public static ProductRequest PIZZA_REQUEST = new ProductRequest("image", "PIZZA", 1000);
    public static ProductRequest CHICKEN_REQUEST = new ProductRequest("image", "CHICKEN", 1000);
    public static ProductRequest RAMYEON_REQUEST = new ProductRequest("image", "RAMYEON", 1000);
    public static ProductRequest SANDWICH_REQUEST = new ProductRequest("image", "SANDWICH", 1000);
    public static ProductRequest COFFEE_REQUEST = new ProductRequest("image", "COFFEE", 1000);
    public static ProductRequest COKE_REQUEST = new ProductRequest("image", "COKE", 1000);
    public static ProductRequest WATER_REQUEST = new ProductRequest("image", "WATER", 1000);
    public static ProductRequest SNACK_REQUEST = new ProductRequest("image", "SNACK", 1000);

    public static ProductResponse PIZZA_RESPONSE = new ProductResponse(1L, "image", "PIZZA", 1000);
    public static ProductResponse CHICKEN_RESPONSE = new ProductResponse(1L, "image", "CHICKEN", 1000);
    public static ProductResponse RAMYEON_RESPONSE = new ProductResponse(1L, "image", "RAMYEON", 1000);
    public static ProductResponse SANDWICH_RESPONSE = new ProductResponse(1L, "image", "SANDWICH", 1000);
    public static ProductResponse COFFEE_RESPONSE = new ProductResponse(1L, "image", "COFFEE", 1000);
    public static ProductResponse COKE_RESPONSE = new ProductResponse(1L, "image", "COKE", 1000);
    public static ProductResponse WATER_RESPONSE = new ProductResponse(1L, "image", "WATER", 1000);
    public static ProductResponse SNACK_RESPONSE = new ProductResponse(1L, "image", "SNACK", 1000);
}
