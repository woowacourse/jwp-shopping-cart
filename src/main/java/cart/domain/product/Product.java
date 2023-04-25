package cart.domain.product;

public class Product {
    private final ProductName name;
    private final ProductPrice price;
    private final ProductCategory category;

    public Product(ProductName name, ProductPrice price, ProductCategory category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
