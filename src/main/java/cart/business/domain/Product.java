package cart.business.domain;

public class Product {

    private final Integer id;
    private final ProductName name;
    private final ProductImage image;
    private final ProductPrice productPrice;

    public Product(Integer id, ProductName name, ProductImage image, ProductPrice productPrice) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.productPrice = productPrice;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getImage() {
        return image.getValue();
    }

    public Integer getProductPrice() {
        return productPrice.getValue();
    }
}
