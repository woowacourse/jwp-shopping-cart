package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Product {
    private Long id;
    private String name;
    private long price;
    private String imageUrl;

    public Product() {
    }

    public Product(final Long id, final String name, final long price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(final String name, final long price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public boolean isSameId(final long productId) {
        return this.id == productId;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name) && Objects.equals(imageUrl,
                product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, imageUrl);
    }
}
