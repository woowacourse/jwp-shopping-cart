package woowacourse.shoppingcart.domain.customer.password;

import woowacourse.shoppingcart.exception.datanotmatch.CustomerDataNotMatchException;
import woowacourse.shoppingcart.exception.datanotmatch.LoginDataNotMatchException;

public class EncryptedPassword implements Password {

    private final String value;

    public EncryptedPassword(final String value) {
        this.value = value;
    }

    public void validateMatchingLoginPassword(final String other) {
        if (!value.equals(other)) {
            throw new LoginDataNotMatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void validateMatchingOriginalPassword(final String other) {
        if (!value.equals(other)) {
            throw new CustomerDataNotMatchException("기존 비밀번호와 입력한 비밀번호가 일치하지 않습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
