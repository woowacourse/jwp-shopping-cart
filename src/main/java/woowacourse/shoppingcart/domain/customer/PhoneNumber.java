package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

public class PhoneNumber {
    private static final Pattern PATTERN = Pattern.compile("^[0-9]*$");
    private static final int LENGTH = 11;

    private final String value;

    public PhoneNumber(String value) {
        validateLength(value);
        validatePattern(value);
        this.value = value;
    }

    private void validateLength(String value) {
        if (value.length() != LENGTH) {
            throw new IllegalArgumentException("휴대폰 번호는 " + LENGTH + " 자리를 입력해주세요.");
        }
    }

    private void validatePattern(String value) {
        if (!PATTERN.matcher(value).find()) {
            throw new IllegalArgumentException("휴대폰 번호를 정확히 입력해주세요.");
        }
    }

    public PhoneNumber update(String phoneNumber) {
        return new PhoneNumber(phoneNumber);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PhoneNumber that = (PhoneNumber)o;

        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }
}
