package cart.entity;

import cart.domain.Product;

import java.util.Objects;

public class ProductEntity {
    private final Long id;
    private final String name;
    private final String image;
    private final Long price;

    public ProductEntity(final String name, final String image, final Long price) {
        this(null, name, image, price);
    }

    public ProductEntity(final Long id, final String name, final String image, final Long price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }
    public ProductEntity(final Product product) {
        this(product.getId(), product.getName(), product.getImage(), product.getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Long getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
