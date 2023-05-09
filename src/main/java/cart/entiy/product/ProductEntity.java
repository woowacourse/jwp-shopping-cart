package cart.entiy.product;

import cart.domain.product.Product;
import cart.domain.product.ProductId;

public class ProductEntity {

    private final ProductId id;
    private final String name;
    private final String image;
    private final int price;

    public ProductEntity(final ProductId productId, final String name, final String image, final int price) {
        id = productId;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public ProductEntity(final Long id, final String name, final String image, final int price) {
        this(new ProductId(id), name, image, price);
    }

    public ProductEntity(final String name, final String image, final int price) {
        this(new ProductId(), name, image, price);
    }

    public ProductEntity(final Long id, final ProductEntity other) {
        this(new ProductId(id), other.name, other.image, other.price);
    }

    public static ProductEntity from(final Product product) {
        return new ProductEntity(
                product.getProductId(),
                product.getProductName(),
                product.getProductImage().getValue(),
                product.getProductPrice().getValue());
    }

    public Product toDomain() {
        return new Product(id, name, image, price);
    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
