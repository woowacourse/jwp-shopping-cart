package cart.entity.product;

final class Price {

    private static final int MIN_PRICE = 0;

    private final Integer value;

    public Price(final Integer value) {
        validate(value);
        this.value = value;
    }

    private void validate(final Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("상품의 가격이 존재하지 않습니다.");
        }
        if (value < MIN_PRICE) {
            throw new IllegalArgumentException(String.format("상품의 가격은 %d보다 높아야합니다.", MIN_PRICE));
        }
    }

    public Integer getValue() {
        return value;
    }
}
