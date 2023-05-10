package cart.domain.product;

public class Price {

    private static final String NOT_POSITIVE_ERROR_MESSAGE = "가격은 0보다 커야합니다.";

    private final int price;

    public Price(final int price) {
        validate(price);
        this.price = price;
    }

    private void validate(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException(NOT_POSITIVE_ERROR_MESSAGE);
        }
    }

    public int getPrice() {
        return price;
    }
}
