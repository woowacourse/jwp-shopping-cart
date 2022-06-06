package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.util.StringUtil;

public class Username {

    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MAX_USERNAME_LENGTH = 20;

    private final String value;

    public Username(String value) {
        validateUsername(value);
        this.value = value;
    }

    private void validateUsername(String value) {
        StringUtil.validateLength(value, MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Username username = (Username) o;
        return Objects.equals(value, username.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
