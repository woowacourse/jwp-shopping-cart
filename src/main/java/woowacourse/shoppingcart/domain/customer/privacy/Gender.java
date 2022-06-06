package woowacourse.shoppingcart.domain.customer.privacy;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Gender {
    MALE("male"),
    FEMALE("female"),
    UNDEFINED("undefined"),
    ;

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public static Gender from(String value) {
        return Arrays.stream(values())
                .filter(gender -> gender.value.equals(value))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("입력된 값에 해당하는 성별이 존재하지 않습니다."));
    }

    public String getValue() {
        return value;
    }
}
