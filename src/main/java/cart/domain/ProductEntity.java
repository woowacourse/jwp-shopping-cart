package cart.domain;

import java.util.Objects;

public class ProductEntity {

    private static final int MAXIMUM_NAME_LENGTH = 50;
    private static final int MINIMUM_PRICE = 0;
    private static final int MAXIMUM_PRICE = 1_000_000_000;
    private static final int MAXIMUM_IMAGE_LENGTH = 2_000;

    private final Long id;
    private final String name;
    private final Integer price;
    private final String image;

    public ProductEntity(final Long id, final String name, final Integer price, final String image) {
        validate(name, price, image);
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public ProductEntity(final String name, final Integer price, final String image) {
        this(null, name, price, image);
    }

    private void validate(final String name, final Integer price, final String image) {
        if (name.length() > MAXIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException("상품 이름은 " + MAXIMUM_NAME_LENGTH + "자를 넘길 수 없습니다.");
        }
        if (price < MINIMUM_PRICE || price > MAXIMUM_PRICE) {
            throw new IllegalArgumentException("가격은 " + MINIMUM_PRICE + " 미만이거나, " + MAXIMUM_PRICE + " 초과일 수 없습니다.");
        }
        if (image.length() > MAXIMUM_IMAGE_LENGTH) {
            throw new IllegalArgumentException("이미지 주소는 " + MAXIMUM_IMAGE_LENGTH + "자를 넘길 수 없습니다.");
        }
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
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, image);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
