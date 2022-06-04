package woowacourse.shoppingcart.domain.customer;

import org.springframework.util.StringUtils;
import woowacourse.shoppingcart.exception.InvalidArgumentRequestException;

import java.util.Objects;

public class Username {
    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MAX_USERNAME_LENGTH = 20;

    private final String username;

    public Username(String username) {
        validateUserName(username);
        this.username = username;
    }

    private void validateUserName(String username) {
        if (!StringUtils.hasText(username)) {
            throw new InvalidArgumentRequestException("아이디는 공백일 수 없습니다.");
        }
        if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH) {
            throw new InvalidArgumentRequestException("아이디의 길이는 4자 이상 20자 이하여야 합니다.");
        }
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Username username1 = (Username) o;
        return Objects.equals(username, username1.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
