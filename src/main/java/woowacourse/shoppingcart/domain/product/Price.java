package woowacourse.shoppingcart.domain.product;

public class Price {

    private final int price;

    public Price(final int price) {
        this.price = price;
    }

    public int getValue() {
        return price;
    }
}
