package cart.domain.product;

import java.util.Objects;

public class Product {

    private final Long id;
    private final Name name;
    private final Price price;
    private final String imageUrl;

    public Product(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = new Name(name);
        this.price = new Price(price);
        this.imageUrl = imageUrl;
    }

    public Product(String name, int price, String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public int getPrice() {
        return price.getAmount();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id)
                && Objects.equals(name, product.name)
                && Objects.equals(price, product.price)
                && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, imageUrl);
    }
}
