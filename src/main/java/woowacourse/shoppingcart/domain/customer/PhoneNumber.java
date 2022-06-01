package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumber {

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");

    private final String value;

    public PhoneNumber(String value) {
        validatePhoneNumber(value);
        this.value = value;
    }

    private void validatePhoneNumber(String value) {
        checkNull(value);
        checkFormat(value);
    }

    private void checkNull(String value) {
        if (value == null) {
            throw new NullPointerException("전화번호는 필수 입력 사항압니다.");
        }
    }

    private void checkFormat(String value) {
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다. (형식: 000-0000-0000)");
        }
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
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
