package cart.repository.entity;

import static cart.domain.Price.LOWER_BOUND;
import static cart.domain.Price.UPPER_BOUND;

import cart.domain.Product;
import java.util.Objects;

public class ProductEntity {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public ProductEntity(final Long id, final String name, final String imageUrl, final int price) {
        validate(name, imageUrl, price);
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public ProductEntity(final String name, final String imageUrl, final int price) {
        this(null, name, imageUrl, price);
    }

    private void validate(final String name, final String imageUrl, final int price) {
        validateName(name);
        validateImageUrl(imageUrl);
        validatePrice(price);
    }

    private void validateName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }
    }

    private void validateImageUrl(final String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("이미지 경로는 공백일 수 없습니다.");
        }
    }

    private void validatePrice(final int price) {
        if (price < LOWER_BOUND || price > UPPER_BOUND) {
            throw new IllegalArgumentException("가격은 10억 이하의 음이 아닌 정수여야 합니다.");
        }
    }

    public static ProductEntity from(final Product product) {
        return new ProductEntity(
                product.getName().getValue(),
                product.getImageUrl().getValue(),
                product.getPrice().getValue());
    }

    public static ProductEntity of(final Long id, final Product product) {
        return new ProductEntity(
                id,
                product.getName().getValue(),
                product.getImageUrl().getValue(),
                product.getPrice().getValue());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductEntity that = (ProductEntity) o;
        return price == that.price && Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, price);
    }
}
