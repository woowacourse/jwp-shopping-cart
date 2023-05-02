package cart.domain;

public class Price {

    private static final int MIN_PRICE_VALUE = 0;
    private long price;

    public Price(final long price) {
        validatePrice(price);
        this.price = price;
    }

    private void validatePrice(final long price) {
        if (price < MIN_PRICE_VALUE) {
            throw new IllegalArgumentException("상품 가격은 " + MIN_PRICE_VALUE + "원 이상이여야 합니다.");
        }
    }

    public long getPrice() {
        return price;
    }
}
