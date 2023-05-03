package cart.vo;

import java.util.Objects;

import static cart.vo.util.VoUtil.isInvalidForm;
import static cart.vo.util.VoUtil.isInvalidLength;

public class Password {

    private static final int EMAIL_LENGTH_LOWER_BOUND_EXCLUSIVE = 1;
    private static final int EMAIL_LENGTH_UPPER_BOUND_EXCLUSIVE = 20;
    private final String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password from(String value) {
        validate(value);
        return new Password(value);
    }

    public static void validate(String value) {
        if (isInvalidForm(value, "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$")
                || isInvalidLength(value, EMAIL_LENGTH_LOWER_BOUND_EXCLUSIVE, EMAIL_LENGTH_UPPER_BOUND_EXCLUSIVE)) {
            throw new IllegalStateException("올바르지 않은 비밀번호 형식입니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
