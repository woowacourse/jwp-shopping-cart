package cart.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {

    private static final BigDecimal MIN_AMOUNT = new BigDecimal("1,000".replaceAll(",", ""));
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("1,000,000".replaceAll(",", ""));

    private final BigDecimal amount;

    public Price(BigDecimal amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("가격은 1000원 이상, 100만원 이하만 가능합니다.");
        }

        if (amount.compareTo(MIN_AMOUNT) < 0 || amount.compareTo(MAX_AMOUNT) > 0) {
            throw new IllegalArgumentException("가격은 1000원 이상, 100만원 이하만 가능합니다.");
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return amount.compareTo(price.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
