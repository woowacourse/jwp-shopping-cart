package cart.domain;

import cart.dao.entity.ProductEntity;

import java.util.Objects;

public class Product {

    private final String name;
    private final Integer price;
    private final String image;

    public Product(final String name, final Integer price, final String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product(final ProductEntity productEntity) {
        this.name = productEntity.getName();
        this.price = productEntity.getPrice();
        this.image = productEntity.getImage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(image, product.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, image);
    }
}
