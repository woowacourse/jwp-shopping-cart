package cart.domain;

import java.text.DecimalFormat;
import java.util.Objects;

public class Price {

    public static final int MIN_PRICE = 0;
    public static final int MAX_PRICE = 500_000_000;
    private static final String FORMATTED_MAX_PRICE = new DecimalFormat("###,###").format(MAX_PRICE);
    private final int price;

    public Price(int price) {
        this.price = validate(price);
    }

    private int validate(int price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException(
                    "상품의 가격은 " + MIN_PRICE + "원 이상 " + FORMATTED_MAX_PRICE + "원 이하여야 합니다. (현재 " + price + "원)");
        }
        return price;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price1 = (Price) o;
        return price == price1.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public String toString() {
        return "Price{" +
                "price=" + price +
                '}';
    }
}
