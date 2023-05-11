package cart.dao.entity;

import java.util.Objects;

public class ProductEntity {

    public static final int MAXIMUM_NAME_LENGTH = 50;
    public static final int MINIMUM_PRICE = 0;
    public static final int MAXIMUM_PRICE = 1_000_000_000;
    public static final int MAXIMUM_IMAGE_LENGTH = 2_000;

    private final Long id;
    private final String name;
    private final Integer price;
    private final String image;

    private ProductEntity(final Long id, final String name, final Integer price, final String image) {
        validate(name, price, image);
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
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

    public static class Builder {

        private Long id;
        private String name;
        private Integer price;
        private String image;

        public ProductEntity build() {
            return new ProductEntity(id, name, price, image);
        }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder price(final Integer price) {
            this.price = price;
            return this;
        }

        public Builder image(final String image) {
            this.image = image;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, image);
    }
}
