package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;

public class Name {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]*$");

    private final String value;

    public Name(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!NAME_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("유효한 이름의 형식이 아닙니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
