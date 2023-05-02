package cart.business.domain;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class Product {

    private final Integer id;
    private final ProductName name;
    private final ProductImage url;
    private final ProductPrice price;

    public Product(@NonNull Integer id, @NonNull ProductName name,
                   @NonNull ProductImage url, @NonNull ProductPrice price) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.getValue().equals(product.getName())
                && url.getValue().equals(product.getUrl())
                && price.getValue() == product.getPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, price);
    }
}
