package woowacourse.shoppingcart.domain.customer.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public class PhoneNumber {

    private static final Pattern FORMAT = Pattern.compile("^[0-9]+$");
    private static final int START_LENGTH = 3;
    private static final int MIDDLE_LENGTH = 4;
    private static final int LAST_LENGTH = 4;

    private final String value;

    public PhoneNumber(String start, String middle, String last) {
        validate(start, middle, last);
        this.value = start + middle + last;
    }

    private void validate(String start, String middle, String last) {
        validateLength(start, middle, last);
        validateNumeric(start, middle, last);
    }

    private void validateLength(String start, String middle, String last) {
        if (start.length() != START_LENGTH || middle.length() != MIDDLE_LENGTH
                || last.length() != LAST_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("휴대폰 번호양식이 불일치 합니다. %s-%s-%s", start, middle, last));
        }
    }

    private void validateNumeric(String start, String middle, String last) {
        if (!FORMAT.matcher(start + middle + last).matches()) {
            throw new IllegalArgumentException(
                    String.format("휴대폰 번호는 숫자만 가능합니다. %s-%s-%s", start, middle, last));
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhoneNumber)) {
            return false;
        }
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
