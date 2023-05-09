package cart.vo;

import java.util.Objects;

import static cart.vo.util.VoUtil.isInvalidLength;

public class Name {

    private static final int NAME_LENGTH_LOWER_BOUND_EXCLUSIVE = 1;
    private static final int NAME_LENGTH_UPPER_BOUND_EXCLUSIVE = 100;

    private final String value;

    public Name(String value) {
        this.value = value;
    }

    public static Name from(String value) {
        validateLength(value);
        return new Name(value);
    }

    private static void validateLength(String value) {
        if (isInvalidLength(value, NAME_LENGTH_LOWER_BOUND_EXCLUSIVE, NAME_LENGTH_UPPER_BOUND_EXCLUSIVE)) {
            throw new IllegalStateException("올바르지 않은 이름입니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
