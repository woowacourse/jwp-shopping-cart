package cart.business.domain.product;

import java.util.Objects;

public class Product {

    private final Integer id;
    private final ProductName name;
    private final ProductImage url;
    private final ProductPrice price;

    public Product(Integer id, ProductName name, ProductImage url, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getUrl() {
        return url.getValue();
    }

    public Integer getPrice() {
        return price.getValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
