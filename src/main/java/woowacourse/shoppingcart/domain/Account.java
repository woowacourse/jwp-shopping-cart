package woowacourse.shoppingcart.domain;

import java.util.Locale;

public class Account {

    private static final String MATCH_PATTERN = "[^\\da-zA-Z]";

    private final String value;

    public Account(String value) {
        this.value = convert(value);
    }

    private String convert(String value) {
        return value.replaceAll(MATCH_PATTERN, "").toLowerCase(Locale.ROOT).trim();
    }

    public String getValue() {
        return value;
    }
}
