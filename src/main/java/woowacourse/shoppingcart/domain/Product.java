package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.common.exception.InvalidRequestException;

public class Product {

    private static final int MAX_NAME_LENGTH = 255;
    private static final int MIN_PRICE_VALUE = 0;

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public Product(Long id, String name, int price, String imageUrl) {
        validate(name);
        validate(price);
        this.id = id;
        this.name = name.trim();
        this.price = price;
        this.imageUrl = imageUrl.trim();
    }

    private void validate(String name) {
        if (name.isBlank()) {
            throw new InvalidRequestException("상품명 정보가 누락되었습니다.");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new InvalidRequestException("상품명이 최대 글자수를 초과하였습니다.");
        }
    }

    private void validate(int price) {
        if (price < MIN_PRICE_VALUE) {
            throw new InvalidRequestException("상품 가격은 음수일 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return price == product.price
                && Objects.equals(id, product.id)
                && Objects.equals(name, product.name)
                && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, imageUrl);
    }
}
