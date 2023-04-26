package cart.domain;

import java.math.BigDecimal;

public class Price {
    private final Integer value;

    public Price(BigDecimal value) {
        validatePrice(value);
        this.value = value.intValue();
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 음수 혹은 빈 값이 될 수 없습니다.");
        }
        if (price.compareTo(new BigDecimal(1_000_000_000)) > 0) {
            throw new IllegalArgumentException("가격은 10억을 초과할 수 없습니다.");
        }
    }

    public Integer getValue() {
        return value;
    }
}
