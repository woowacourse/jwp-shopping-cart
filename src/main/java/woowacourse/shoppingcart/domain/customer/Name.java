package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;
import woowacourse.auth.exception.BadRequestException;

public class Name {

    private static final String REGULAR_EXPRESSION = "(^[ㄱ-ㅎㅓ-ㅣ가-힣]).{1,4}$";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(REGULAR_EXPRESSION);
    static final String INVALID_NAME_FORMAT = "이름은 최대 5글자, 한글로 이뤄져야합니다.";

    private final String value;

    public Name(final String target) {
        validateName(target);
        this.value = target;
    }

    private void validateName(String target) {
        if (!COMPILED_PATTERN.matcher(target).matches()) {
            throw new BadRequestException(INVALID_NAME_FORMAT);
        }
    }

    public String getValue() {
        return value;
    }
}
