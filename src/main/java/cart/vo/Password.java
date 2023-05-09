package cart.vo;

import java.util.Objects;
import java.util.regex.Pattern;

import static cart.vo.util.VoUtil.isInvalidLength;

public class Password {

    private static final int EMAIL_LENGTH_LOWER_BOUND_EXCLUSIVE = 1;
    private static final int EMAIL_LENGTH_UPPER_BOUND_EXCLUSIVE = 20;
    private static final String VALID_PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(VALID_PASSWORD_REGEX);

    private final String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password from(String value) {
        validate(value);
        return new Password(value);
    }

    public static void validate(String value) {
        if (!PASSWORD_PATTERN.matcher(value).matches()
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
