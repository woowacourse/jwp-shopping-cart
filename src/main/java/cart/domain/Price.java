package cart.domain;

public class Price {

    public static final int MIN_PRICE_EXCLUSIVE = 1000;
    private final Integer price;

    public Price(Integer price) {
        this.price = price;
    }

    public static Price of(Integer price) {
        if (price < MIN_PRICE_EXCLUSIVE) {
            throw new IllegalArgumentException("최소 1000원 이상의 가격을 입력해 주세요.");
        }
        return new Price(price);
    }

    public Integer getPrice() {
        return price;
    }
}
