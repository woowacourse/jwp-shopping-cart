package cart.vo;

import java.util.Objects;
import java.util.regex.Pattern;

import static cart.vo.util.VoUtil.isInvalidLength;

public class Email {

    private static final int EMAIL_LENGTH_LOWER_BOUND_EXCLUSIVE = 1;
    private static final int EMAIL_LENGTH_UPPER_BOUND_EXCLUSIVE = 30;
    private static final String VALID_EMAIL_REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(VALID_EMAIL_REGEX);

    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email from(String value) {
        validate(value);
        return new Email(value);
    }

    private static void validate(String value) {
        if (!EMAIL_PATTERN.matcher(value).matches()
                || isInvalidLength(value, EMAIL_LENGTH_LOWER_BOUND_EXCLUSIVE, EMAIL_LENGTH_UPPER_BOUND_EXCLUSIVE)) {
            throw new IllegalStateException("올바르지 않은 이메일 형식입니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
