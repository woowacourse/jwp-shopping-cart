package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

class Password {

    private final String value;

    Password(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        checkNull(value);
        checkLength(value);
        checkEmptySpace(value);
    }

    private void checkNull(String value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("비밀번호는 Null 일 수 없습니다.");
        }
    }

    private void checkEmptySpace(String value) {
        if (value.length() > value.replaceFirst(" ", "").length()) {
            throw new IllegalArgumentException("비밀번호에는 공백이 들어가면 안됩니다.");
        }
    }

    private void checkLength(String value) {
        int length = value.length();
        if (length < 8 || length > 20) {
            throw new IllegalArgumentException("비밀번호는 8자 이상 20자 이하여야합니다.");
        }
    }

    String getValue() {
        return value;
    }
}
