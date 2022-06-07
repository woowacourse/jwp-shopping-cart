package woowacourse.shoppingcart.domain.product;

public class Price {

    private final int price;

    public Price(int price) {
        this.price = price;
    }

    public int getValue() {
        return price;
    }
}
