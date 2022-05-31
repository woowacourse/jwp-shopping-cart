package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

public class Name {

    private static final String REGULAR_EXPRESSION = "(^[ㄱ-ㅎㅓ-ㅣ가-힣]).{1,4}$";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(REGULAR_EXPRESSION);
    static final String INVALID_NAME_FORMAT = "올바르지 않은 이름입니다.";

    private final String value;

    public Name(final String target) {
        validateName(target);
        this.value = target;
    }

    private void validateName(String target) {
        if (!COMPILED_PATTERN.matcher(target).matches()) {
            throw new IllegalArgumentException(INVALID_NAME_FORMAT);
        }
    }

    public String getValue() {
        return value;
    }
}
