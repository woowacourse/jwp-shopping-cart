package cart.vo;

public class Price {

    private static final int PRICE_LOWER_BOUND_EXCLUSIVE = 1;
    private static final int PRICE_UPPER_BOUND_EXCLUSIVE = 1_000_000_000;
    private final int value;

    private Price(int value) {
        this.value = value;
    }

    public static Price from(int value) {
        validateRange(value);
        return new Price(value);
    }

    private static void validateRange(int value) {
        if (isInvalidRange(value)) {
            throw new IllegalStateException("올바르지 않은 가격입니다.");
        }
    }

    private static boolean isInvalidRange(int value) {
        return value < PRICE_LOWER_BOUND_EXCLUSIVE || PRICE_UPPER_BOUND_EXCLUSIVE < value;
    }


    public int getValue() {
        return value;
    }

}
