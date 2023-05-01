package cart.domain.product;

public final class Price {

    private static final int MIN = 0;
    private static final int MAX = 10_000_000;

    private final Integer price;

    public Price(final Integer price) {
        validatePrice(price);
        this.price = price;
    }

    private void validatePrice(final Integer price) {
        if (price < MIN || price > MAX) {
            throw new IllegalArgumentException("상품 가격은 " + MIN + " ~ " + MAX + "원까지 가능합니다.");
        }
    }

    public Integer getPrice() {
        return price;
    }
}
