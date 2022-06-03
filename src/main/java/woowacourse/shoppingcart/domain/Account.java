package woowacourse.shoppingcart.domain;

import java.util.Locale;

public class Account {

    private static final int MINIMUM_LENGTH = 4;
    private static final int MAXIMUM_LENGTH = 15;
    private static final String MATCH_PATTERN = "[^\\da-zA-Z]";

    private final String value;

    public Account(String value) {
        validateBlank(value);
        validateLength(value);
        this.value = convert(value);
    }

    private void validateBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("아이디는 비어있을 수 없습니다.");
        }
    }

    private void validateLength(String value) {
        if (value.length() < MINIMUM_LENGTH || value.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException("아이디 길이는 " + MINIMUM_LENGTH + "~" + MAXIMUM_LENGTH + "자를 만족해야 합니다.");
        }
    }

    private String convert(String value) {
        return value.replaceAll(MATCH_PATTERN, "").toLowerCase(Locale.ROOT).trim();
    }

    public String getValue() {
        return value;
    }
}
