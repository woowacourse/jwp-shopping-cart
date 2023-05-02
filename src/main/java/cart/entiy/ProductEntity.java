package cart.entiy;

import cart.domain.Product;

public class ProductEntity {

    private final ProductEntityId id;
    private final String name;
    private final String image;
    private final int price;

    public ProductEntity(final String name, final String image, final int price) {
        this(null, name, image, price);
    }

    public ProductEntity(final Long id, final ProductEntity other) {
        this(id, other.name, other.image, other.price);
    }

    public ProductEntity(final Long id, final String name, final String image, final int price) {
        this.id = new ProductEntityId(id);
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductEntity from(final Product product) {
        return new ProductEntity(
                product.getProductId(),
                product.getProductName(),
                product.getProductImage().getValue(),
                product.getProductPrice().getValue());
    }

    public Product toDomain() {
        return new Product(id.getValue(), name, image, price);
    }

    public ProductEntityId getId() {
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
