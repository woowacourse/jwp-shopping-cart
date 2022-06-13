package woowacourse.shoppingcart.domain.customer;

import woowacourse.auth.exception.BadRequestException;

public class Terms {

    static final String DISAGREED_TERMS = "약관에 동의하지 않았습니다.";

    private final boolean value;

    public Terms(final boolean target) {
        validateTerms(target);
        this.value = target;
    }

    private void validateTerms(boolean target) {
        if (!target) {
            throw new BadRequestException(DISAGREED_TERMS);
        }
    }

    public boolean isValue() {
        return value;
    }
}
