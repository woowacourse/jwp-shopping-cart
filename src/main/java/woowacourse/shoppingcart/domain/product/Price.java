package woowacourse.shoppingcart.domain.product;

public class Price {

    private final int price;

    public Price(int price) {
        validateOverZero(price);
        this.price = price;
    }

    private void validateOverZero(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("가격은 최소 1원이어야 합니다.");
        }
    }

    public int getPrice() {
        return price;
    }
}
