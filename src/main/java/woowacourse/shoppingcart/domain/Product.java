package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidCartItemException;

public class Product {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int stock;

    public Product(final Long id, final String name, final int price, final String imageUrl, final int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public void validateStock(int quantity) {
        if (stock < quantity) {
            throw new InvalidCartItemException("재고가 부족합니다. 현재 재고: " + stock);
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }
}
