package cart.domain.cart;

public class Quantity {

    private static final String LOWER_BOUNDARY_ERROR_MESSAGE = "수량은 0보다 작을 수 없습니다.";

    private final int value;

    public Quantity(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int value) {
        if (value < 0) {
            throw new IllegalArgumentException(LOWER_BOUNDARY_ERROR_MESSAGE);
        }
    }

    public int getValue() {
        return value;
    }
}
