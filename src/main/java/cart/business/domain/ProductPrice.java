package cart.business.domain;

import org.springframework.lang.NonNull;

public class ProductPrice {

    private static final int MIN_PRICE = 0;
    private static final int MAX_PRICE = 1_000_000;
    private final int price;

    public ProductPrice(@NonNull int price) {
        validate(price);
        this.price = price;
    }

    private void validate(int price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException(String.format("가격은 %d이상 %d이하 이어야 합니다.", MIN_PRICE, MAX_PRICE));
        }
    }

    public int getValue() {
        return price;
    }
}
