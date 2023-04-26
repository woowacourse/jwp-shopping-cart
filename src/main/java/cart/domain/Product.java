package cart.domain;

public class Product {

    private static final int MAXIMUM_NAME_LENGTH = 50;
    private static final int MINIMUM_PRICE = 0;
    private static final int MAXIMUM_PRICE = 1_000_000_000;
    private static final int MAXIMUM_IMAGE_LENGTH = 2_000;

    private final String name;
    private final Integer price;
    private final String image;

    public Product(final String name, final Integer price, final String image) {
        validate(name, price, image);
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
}
