package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidProductException;

public class Product {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    public Product(final Long id, final String name, final int price, final String imageUrl) {
        validateRightPrice(price);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    private void validateRightPrice(final int price) {
        if (price < 0) {
            throw new InvalidProductException("[ERROR] 제품의 적절히 가격을 설정해주세요.");
        }
    }

    public Product(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
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
}
