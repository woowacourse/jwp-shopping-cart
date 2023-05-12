package cart.domain;

import cart.excpetion.product.InvalidProductException;

import java.util.Objects;

public class Product {
    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 20;
    private final Integer id;
    private final String name;
    private final String image;
    private final Integer price;

    public Product(final Integer id, final String name, final String image, final Integer price) {
        validate(name, image, price);
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Product(final String name, final String image, final Integer price) {
        this(null, name, image, price);
    }

    private void validate(final String name, final String image, final Integer price) {
        validateName(name);
        validateImage(image);
        validatePrice(price);
    }

    private void validateName(final String name) {
        if (name == null) {
            throw new InvalidProductException("상품의 이름은 필수 입니다.");
        }
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new InvalidProductException(String.format("상품의 이름은 %d이상 %d이하 입니다", MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        }
    }

    private void validateImage(final String image) {
        if (image == null) {
            throw new InvalidProductException("상품은 이미지를 가져야 합니다.");
        }
    }

    private void validatePrice(final Integer price) {
        if (price == null) {
            throw new InvalidProductException("상품은 가격을 가져야 합니다.");
        }

        if (price < 0) {
            throw new InvalidProductException("상품의 가격은 0보다 커야합니다.");
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
