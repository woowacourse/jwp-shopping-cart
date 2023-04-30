package cart.entity.vo;

public class Price {
    private final Integer price;

    public Price(final Integer price) {
        validate(price);
        this.price = price;
    }

    private void validate(final Integer price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0보다 작을 수 없습니다");
        }
    }

    public Integer value() {
        return price;
    }
}
