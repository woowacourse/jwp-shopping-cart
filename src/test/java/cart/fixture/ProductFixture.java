package cart.fixture;

import cart.domain.Product;
import cart.dto.ProductRequest;

import java.math.BigDecimal;

public class ProductFixture {
    public static Product PIZZA = new Product("PIZZA", "url", BigDecimal.valueOf(10000));
    public static Product CHICKEN = new Product("CHICKEN", "url", BigDecimal.valueOf(10000));
    public static Product RAMYEON = new Product("RAMYEON", "url", BigDecimal.valueOf(10000));
    public static Product SANDWICH = new Product("SANDWICH", "url", BigDecimal.valueOf(10000));
    public static Product COFFEE = new Product("COFFEE", "url", BigDecimal.valueOf(10000));
    public static Product COKE = new Product("COKE", "url", BigDecimal.valueOf(10000));
    public static Product WATER = new Product("WATER", "url", BigDecimal.valueOf(500));
    public static Product SNACK = new Product("SNACK", "url", BigDecimal.valueOf(10000));

    public static ProductRequest PIZZA_REQUEST = new ProductRequest("image", "PIZZA", 1000);
    public static ProductRequest CHICKEN_REQUEST = new ProductRequest("image", "CHICKEN", 1000);
    public static ProductRequest RAMYEON_REQUEST = new ProductRequest("image", "RAMYEON", 1000);
    public static ProductRequest SANDWICH_REQUEST = new ProductRequest("image", "SANDWICH", 1000);
    public static ProductRequest COFFEE_REQUEST = new ProductRequest("image", "COFFEE", 1000);
    public static ProductRequest COKE_REQUEST = new ProductRequest("image", "COKE", 1000);
    public static ProductRequest WATER_REQUEST = new ProductRequest("image", "WATER", 1000);
    public static ProductRequest SNACK_REQUEST = new ProductRequest("image", "SNACK", 1000);
}
