package cart.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private final Long id;
    private final Name name;
    private final String imageUrl;
    private final Price price;

    public Product(Long id, String name, String imageUrl, BigDecimal price) {
        this.id = id;
        this.name = new Name(name);
        this.imageUrl = imageUrl;
        this.price = new Price(price);
    }

    public Product(String name, String imageUrl, BigDecimal price) {
        this(null, name, imageUrl, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BigDecimal getPrice() {
        return price.getAmount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) && name.equals(product.name) && imageUrl.equals(product.imageUrl) && price.equals(product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, price);
    }
}
