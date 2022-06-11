package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

public class Password {
    private static final String KOREAN_REGEX = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
    private static final String BLANK = " ";
    private static final int LOWER_BOUND_LENGTH = 6;

    private final String password;

    public Password(String password) {
        checkValid(password);
        this.password = password;
    }

    private void checkValid(String password) {
        if (password.matches(KOREAN_REGEX)) {
            throw new InvalidCustomerException("[ERROR] 비밀번호에 한글이 포함될수 없습니다.");
        }
        if (password.contains(BLANK)) {
            throw new InvalidCustomerException("[ERROR] 비밀번호에 공백이 포함될수 없습니다.");
        }
        if (password.length() < LOWER_BOUND_LENGTH) {
            throw new InvalidCustomerException("[ERROR] 비밀번호의 길이는 6자 이상이어야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    public String get() {
        return password;
    }
}
