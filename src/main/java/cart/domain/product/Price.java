package cart.domain.product;

public class Price {

    private static final int MIN_PRICE = 1;
    private static final int MAX_PRICE = 10_000_000;
    private final int price;

    private Price(final int price) {
        validate(price);

        this.price = price;
    }

    public static Price from(final int price) {
        return new Price(price);
    }

    private void validate(final int price) {
        if (MIN_PRICE > price || MAX_PRICE < price) {
            throw new IllegalArgumentException(String.format("상품 금액은 최소 %d원, 최대 %d원 입니다", MIN_PRICE, MAX_PRICE));
        }
    }

    public int getPrice() {
        return price;
    }
}
