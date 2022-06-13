package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidProductException;

public class Product {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public Product(Long id, String name, Integer price, String imageUrl) {
        validateNull(name, price, imageUrl);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    private void validateNull(String name, Integer price, String imageUrl) {
        if (name == null || price == null || imageUrl == null) {
            throw new InvalidProductException();
        }
    }

    public Product(String name, int price, String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }
}
