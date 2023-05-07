package cart.domain;

public class Price {

    private final int price;

    private Price(final int price) {
        validate(price);
        this.price = price;
    }

    public static Price from(final int price) {
        return new Price(price);
    }

    private void validate(final int price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0원 이상이어야 합니다");
        }
    }

    public int getPrice() {
        return price;
    }
}
