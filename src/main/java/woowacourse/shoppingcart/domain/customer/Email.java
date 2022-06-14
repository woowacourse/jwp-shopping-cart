package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Pattern;

class Email {

    private static final Pattern PATTERN = Pattern.compile("^(.+)@(.+)$");

    private final String value;

    Email(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        checkNull(value);
        checkEmailForm(value);
        checkLength(value);
        checkEmptySpace(value);
    }

    private void checkEmailForm(String value) {
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }
    }

    private void checkNull(String value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("이메일은 Null 일 수 없습니다.");
        }
    }

    private void checkEmptySpace(String value) {
        if (value.length() > value.replaceFirst(" ", "").length()) {
            throw new IllegalArgumentException("이메일에는 공백이 들어가면 안됩니다.");
        }
    }

    private void checkLength(String value) {
        int length = value.length();
        if (length < 8 || length > 50) {
            throw new IllegalArgumentException("이메일은 8자 이상 50자 이하여야합니다.");
        }
    }

    String getValue() {
        return value;
    }
}
