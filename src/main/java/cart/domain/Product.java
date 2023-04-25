package cart.domain;

import java.util.Objects;

public class Product {
    private final Integer id;
    private final String name;
    private final String image;
    private final Long price;

    public Product(final String name, final String image, final Long price) {
        this(null, name, image, price);
    }

    public Product(final Integer id, final String name, final String image, final Long price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Integer getId() {
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

        Product product = (Product) o;

        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
