package cart.domain;

import cart.exception.ProductNotValidException;
import java.time.LocalDateTime;

public class Product {

    private static final int MAX_NAME_LENGTH = 100;
    private static final int MIN_PRICE_VALUE = 0;

    private final Long id;
    private final String name;
    private final String image;
    private final long price;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Product(final String name, final String image, final long price) {
        this(null, name, image, price, LocalDateTime.now(), LocalDateTime.now());
    }

    public Product(final Long id, final String name, final String image, final long price) {
        this(id, name, image, price, LocalDateTime.now(), LocalDateTime.now());
    }

    public Product(
            final Long id,
            final String name,
            final String image,
            final long price,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        validate(name, image, price);
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void validate(final String name, final String image, final long price) {
        validateName(name);
        validateImage(image);
        validatePrice(price);
    }

    private void validateName(final String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new ProductNotValidException("상품명의 길이는 " + MAX_NAME_LENGTH + "자 이하여야합니다.");
        }
    }

    private void validateImage(final String image) {
        if (image.isBlank()) {
            throw new ProductNotValidException("이미지는 공백일 수 없습니다.");
        }
    }

    private void validatePrice(final long price) {
        if (price < MIN_PRICE_VALUE) {
            throw new ProductNotValidException("상품 가격은 " + MIN_PRICE_VALUE + "원 이상이여야 합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public long getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
