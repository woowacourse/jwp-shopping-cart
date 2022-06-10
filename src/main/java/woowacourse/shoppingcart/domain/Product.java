package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Product {

    private Long id;
    private String name;
    private Integer price;
    private String thumbnailUrl;
    private Integer quantity;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final String thumbnailUrl,
                   final Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.quantity = quantity;
    }

    public static Product createWithoutId(final String name, final int price, final String thumbnailUrl, final Integer quantity) {
        return new Product(null, name, price, thumbnailUrl, quantity);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
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
        return Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(
                thumbnailUrl, product.thumbnailUrl) && Objects.equals(quantity, product.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, thumbnailUrl, quantity);
    }
}
