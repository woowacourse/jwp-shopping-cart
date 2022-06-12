package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.exception.InvalidQuantityException;

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

    public void decreaseQuantity(final int count) {
        if (quantity - count < 0) {
            throw new InvalidQuantityException();
        }

        quantity = quantity - count;
    }

    public boolean isOverCount(final Integer count) {
        return quantity < count;
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
}
