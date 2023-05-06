package cart.domain;

import cart.entity.ProductEntity;

import java.util.Objects;

public class Product {
    private final Long id;
    private final String name;
    private final ProductImage image;
    private final ProductPrice price;

    public Product(final String name, final String image, final Long price) {
        this.id = null;
        this.name = name;
        this.image = new ProductImage(image);
        this.price = new ProductPrice(price);
    }

    public Product(final Long id, final String name, final String image, final Long price) {
        this.id = Long.valueOf(id);
        this.name = name;
        this.image = new ProductImage(image);
        this.price = new ProductPrice(price);
    }

    public static Product from(final ProductEntity productEntity) {
        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getImage(), productEntity.getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image.getImage();
    }

    public Long getPrice() {
        return price.getPrice();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
