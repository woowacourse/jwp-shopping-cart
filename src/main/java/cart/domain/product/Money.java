package cart.domain.product;

import lombok.Getter;

@Getter
public class Money {

    public static final int MIN_AMOUNT = 0;
    public static final int MAX_AMOUNT = 1_000_000_000;

    private final int amount;

    public Money(final int amount) {
        validateAmountInRange(amount);
        this.amount = amount;
    }

    private void validateAmountInRange(final int amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException(String.format("상품 금액은 최소 %d원, 최대 %d원입니다", MIN_AMOUNT, MAX_AMOUNT));
        }
    }
}
