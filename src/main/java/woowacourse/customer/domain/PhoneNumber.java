package woowacourse.customer.domain;

import java.util.regex.Pattern;

import woowacourse.customer.exception.InvalidPhoneNumberException;

public class PhoneNumber {

    private static final Pattern PATTERN = Pattern.compile("^01(?:0|1|[6-9])(\\d{4})(\\d{4})$");

    private final String value;

    public PhoneNumber(final String value) {
        validate(value);

        this.value = value;
    }

    private void validate(final String value) {
        if (!PATTERN.matcher(value).find()) {
            throw new InvalidPhoneNumberException("전화번호를 정확히 입력해주세요.");
        }
    }

    public PhoneNumber update(final String phoneNumber) {
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
