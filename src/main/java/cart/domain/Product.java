package cart.domain;

import java.util.Objects;

public class Product {
    private final String name;
    private final int price;
    private final String imageUrl;

    public Product(String name, int price, String imageUrl) {
        validate(name, price, imageUrl);
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    private void validate(String name, int price, String imageUrl) {
        validateName(name);
        validatePrice(price);
        validateImageUrl(imageUrl);
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("상품의 이름은 빈 값이 될 수 없습니다.");
        }
    }

    private void validatePrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("상품의 가격은 0 이하 일 수 없습니다.");
        }
    }

    private void validateImageUrl(String imageUrl) {
        if (Objects.isNull(imageUrl) || imageUrl.isBlank()) {
            throw new IllegalArgumentException("상품의 이미지 URL은 빈 값이 될 수 없습니다.");
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

    public static final class Builder {
        private String name;
        private int price;
        private String imageUrl;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder price(int val) {
            price = val;
            return this;
        }

        public Builder imageUrl(String val) {
            imageUrl = val;
            return this;
        }

        public Product build() {
            return new Product(name, price, imageUrl);
        }
    }
}
