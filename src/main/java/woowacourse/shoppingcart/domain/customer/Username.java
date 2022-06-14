package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

class Username {

    private final String value;

    Username(String value) {
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
            throw new IllegalArgumentException("닉네임은 Null 일 수 없습니다.");
        }
    }

    private void checkEmptySpace(String value) {
        if (value.length() > value.replaceFirst(" ", "").length()) {
            throw new IllegalArgumentException("닉네임에는 공백이 들어가면 안됩니다.");
        }
    }

    private void checkLength(String value) {
        int length = value.length();
        if (length < 1 || length > 10) {
            throw new IllegalArgumentException("닉네임은 1자 이상 10자 이하여야합니다.");
        }
    }

    String getValue() {
        return value;
    }
}
