package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidInputException;

public class Product {
    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public Product(final Long id, final String name, final int price, final String imageUrl) {
        validateName(name);
        validatePrice(price);
        validateName(imageUrl);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    private void validateName(final String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("부적절한 상품 이름입니다.");
        }
    }

    private void validatePrice(final int price) {
        if (price <= 0) {
            throw new InvalidInputException("부적절한 상품 가격입니다.");
        }
    }

    private void validateImageUrl(final String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new InvalidInputException("부적절한 이미지 URL 입니다.");
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
}
