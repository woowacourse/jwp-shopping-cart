package woowacourse.shoppingcart.domain;

public class Age {

    private static final int MINIMUM_AGE = 0;
    private static final String WRONG_AGE_EXCEPTION = "나이는 0살 이상이여야 합니다.";
    private final int value;

    public Age(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < MINIMUM_AGE) {
            throw new IllegalArgumentException(WRONG_AGE_EXCEPTION);
        }
    }

    public int getValue() {
        return value;
    }
}
