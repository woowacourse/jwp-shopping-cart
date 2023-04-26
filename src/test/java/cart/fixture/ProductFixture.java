package cart.fixture;

import cart.domain.Product;

import java.math.BigDecimal;

public class ProductFixture {
    public static Product PIZZA = new Product("pizza", "url", BigDecimal.valueOf(10000));
    public static Product CHICKEN = new Product("pizza", "url", BigDecimal.valueOf(10000));
    public static Product RAMYEON = new Product("pizza", "url", BigDecimal.valueOf(10000));
    public static Product SANDWICH = new Product("pizza", "url", BigDecimal.valueOf(10000));
    public static Product COFFEE = new Product("pizza", "url", BigDecimal.valueOf(10000));
    public static Product COKE = new Product("pizza", "url", BigDecimal.valueOf(10000));
    public static Product WATER = new Product("pizza", "url", BigDecimal.valueOf(500));
    public static Product SNACK = new Product("pizza", "url", BigDecimal.valueOf(10000));
}
