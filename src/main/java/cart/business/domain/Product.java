package cart.business.domain;

public class Product {

    private final int id;
    private final ProductName name;
    private final ProductImage image;
    private final ProductPrice productPrice;

    public Product(int id, ProductName name, ProductImage image, ProductPrice productPrice) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.productPrice = productPrice;
    }

    public int getId() {
        return id;
    }
}
