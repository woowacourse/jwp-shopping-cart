package woowacourse.shoppingcart.domain.product;

import woowacourse.shoppingcart.exception.attribute.NegativeNumberException;

public class Price {

    private Integer amount;

    public Price(Integer amount) {
        validatePositive(amount);
        this.amount = amount;
    }

    private void validatePositive(Integer amount) {
        if (amount < 0) {
            throw NegativeNumberException.fromName("금액");
        }
    }

    public Integer getAmount() {
        return amount;
    }
}
