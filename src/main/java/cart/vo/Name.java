package cart.vo;

public class Name {

    private static final int NAME_LENGTH_LOWER_BOUND_EXCLUSIVE = 1;
    private static final int NAME_LENGTH_UPPER_BOUND_EXCLUSIVE = 100;

    private final String value;

    public Name(String value) {
        this.value = value;
    }

    public static Name of(String value) {
        validateLength(value);
        return new Name(value);
    }

    private static void validateLength(String value) {
        if (isInvalidLength(value)) {
            throw new IllegalStateException("올바르지 않은 이름입니다.");
        }
    }

    private static boolean isInvalidLength(String value) {
        return value.length() < NAME_LENGTH_LOWER_BOUND_EXCLUSIVE || NAME_LENGTH_UPPER_BOUND_EXCLUSIVE < value.length();
    }

    public String getValue() {
        return value;
    }

}
