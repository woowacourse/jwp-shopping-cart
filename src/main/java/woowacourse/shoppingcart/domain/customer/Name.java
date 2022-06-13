package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.InvalidCustomerException;

public class Name {

    private static final String BLANK = " ";
    private static final int UPPER_BOUND_LENGTH = 32;

    private final String name;

    public Name(final String name) {
        checkValid(name);
        this.name = name;
    }

    private void checkValid(final String name) {
        if (name.contains(BLANK)) {
            throw new InvalidCustomerException("[ERROR] 이름에는 공백이 포함될 수 없습니다.");
        }

        if (name.length() > UPPER_BOUND_LENGTH) {
            throw new InvalidCustomerException("[ERROR] 이름의 길이는 32자를 초과할 수 없습니다.");
        }
    }

    public String get() {
        return name;
    }
}
