package woowacourse.shoppingcart.domain.customer;

import java.util.Arrays;

public enum Gender {

    MALE("male"),
    FEMALE("female"),
    UNDEFINED("undefined");

    private final String value;

    Gender(final String value) {
        this.value = value;
    }

    public static Gender form(String target) {
        return Arrays.stream(values())
                .filter(gender -> gender.value.equals(target))
                .findFirst()
                .orElse(UNDEFINED);
    }

    public String getValue() {
        return value;
    }
}
