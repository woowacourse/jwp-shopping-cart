package woowacourse.shoppingcart.domain.customer.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public class PhoneNumber {

    private static final Pattern FORMAT = Pattern.compile("^[0-9]+$");
    private static final int MAX_LENGTH = 11;

    private final String value;

    public PhoneNumber(String value) {
        validate(value);
        this.value = value;
    }

    public PhoneNumber(String start, String middle, String last) {
        this(start + middle + last);
    }

    private void validate(String value) {
        validateLength(value);
        validateNumeric(value);
    }

    private void validateLength(String value) {
        if (value.length() != MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("휴대폰 번호양식이 불일치 합니다. %s", value));
        }
    }

    private void validateNumeric(String value) {
        if (!FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException(String.format("휴대폰 번호는 숫자만 가능합니다. %s", value));
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
