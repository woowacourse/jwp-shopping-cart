package woowacourse.shoppingcart.domain.cart;

import woowacourse.shoppingcart.exception.attribute.NegativeNumberException;

public class Quantity {

    private final Integer amount;

    public Quantity(Integer amount) {
        validatePositive(amount);
        this.amount = amount;
    }

    private void validatePositive(Integer amount) {
        if (amount < 0) {
            throw NegativeNumberException.fromName("수량");
        }
    }

    public Integer getAmount() {
        return amount;
    }
}
