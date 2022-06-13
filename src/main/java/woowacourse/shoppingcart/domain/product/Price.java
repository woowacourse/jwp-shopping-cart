package woowacourse.shoppingcart.domain.product;

import woowacourse.shoppingcart.exception.InvalidProductException;

public class Price {

    private final int price;

    public Price(int price) {
        validateOverZero(price);
        this.price = price;
    }

    private void validateOverZero(int price) {
        if (price <= 0) {
            throw new InvalidProductException("가격은 최소 1원이어야 합니다.");
        }
    }

    public int getPrice() {
        return price;
    }
}
