package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.Objects;

public class Quantity {

    private final int quantity;

    public Quantity(int quantity) {
        validatePositiveQuantity(quantity);
        this.quantity = quantity;
    }

    private void validatePositiveQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidCartItemException("[ERROR] 상품 수는 자연수여야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity1 = (Quantity) o;
        return quantity == quantity1.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }

    public int getQuantity() {
        return quantity;
    }
}
