package cart.repository.entity;

import cart.domain.Product;

public class ProductEntity {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public ProductEntity(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public ProductEntity(final String name, final String imageUrl, final int price) {
        this(null, name, imageUrl, price);
    }

    public static ProductEntity from(final Product product) {
        return new ProductEntity(
                product.getName().getValue(),
                product.getImageUrl().getValue(),
                product.getPrice().getValue());
    }

    public static ProductEntity of(final Long id, final Product product) {
        return new ProductEntity(
                id,
                product.getName().getValue(),
                product.getImageUrl().getValue(),
                product.getPrice().getValue());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
