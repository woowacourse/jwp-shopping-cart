package cart.domain;

public class Product {

    private final ProductName name;
    private final ProductImage image;
    private final ProductPrice productPrice;

    public Product(ProductName name, ProductImage image, ProductPrice productPrice) {
        this.name = name;
        this.image = image;
        this.productPrice = productPrice;
    }
}
