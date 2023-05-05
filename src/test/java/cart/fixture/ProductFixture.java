package cart.fixture;

import cart.service.product.Product;
import cart.service.product.ProductImage;
import cart.service.product.ProductName;
import cart.service.product.ProductPrice;
import cart.service.product.dto.ProductResponse;

public class ProductFixture {
    public static Product PIZZA = new Product(new ProductName("PIZZA"), new ProductImage("url"), new ProductPrice(10000));
    public static Product RAMYEON = new Product(new ProductName("RAMYEON"), new ProductImage("url"), new ProductPrice(10000));
    public static Product COFFEE = new Product(new ProductName("COFFEE"), new ProductImage("url"), new ProductPrice(10000));
    public static Product WATER = new Product(new ProductName("WATER"), new ProductImage("url"), new ProductPrice(10000));
    public static Product SNACK = new Product(new ProductName("SNACK"), new ProductImage("url"), new ProductPrice(10000));


    public static ProductResponse CHICKEN_RESPONSE = new ProductResponse(1L, "image", "CHICKEN", 1000);
}
