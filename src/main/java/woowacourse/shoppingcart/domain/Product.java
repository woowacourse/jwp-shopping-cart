package woowacourse.shoppingcart.domain;

import org.springframework.util.StringUtils;
import woowacourse.shoppingcart.exception.InvalidArgumentRequestException;

import java.util.Objects;

public class Product {
    private static final int PRODUCT_NAME_MAX_LENGTH = 255;

    private final Long id;
    private final String name;
    private final int price;
    private final String thumbnail;

    public Product(final Long id, final String name, final int price, final String thumbnail) {
        validateProductName(name);
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public Product(final String name, final int price, final String thumbnail) {
        this(null, name, price, thumbnail);
    }

    private void validateProductName(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new InvalidArgumentRequestException("상품명은 공백일 수 없습니다.");
        }
        if (name.length() > PRODUCT_NAME_MAX_LENGTH) {
            throw new InvalidArgumentRequestException("상품명은 255자 이하여야 합니다.");
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
