package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.exception.InvalidUsernameException;

public class Username {

    private final String value;

    public Username(String value) {
        validateNull(value);
        validateSpace(value);
        validateLength(value);
        this.value = value;
    }

    private void validateNull(String value) {
        if (value == null) {
            throw new InvalidUsernameException();
        }
    }

    private void validateSpace(String value) {
        if (value.contains(" ")) {
            throw new InvalidUsernameException("회원 이름에는 공백이 들어갈 수 없습니다.");
        }
    }

    private void validateLength(String value) {
        if (value.length() > 32) {
            throw new InvalidUsernameException("회원 이름은 32자 이하입니다.");
        }
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
