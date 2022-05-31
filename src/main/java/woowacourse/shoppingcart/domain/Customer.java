package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidInputException;

public class Customer {
    private static final Pattern usernamePattern =
            Pattern.compile("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    private static final Pattern passwordPattern =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$");

    private final Long id;
    private final String username;
    private final String password;
    private final String nickname;
    private final boolean withdrawal;

    public Customer(final Long id,
                    final String username,
                    final String password,
                    final String nickname,
                    final boolean withdrawal) {
        validateUsername(username);
        validatePassword(password);
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.withdrawal = withdrawal;
    }

    private void validateUsername(final String username) {
        if (!usernamePattern.matcher(username).matches()) {
            throw new InvalidInputException("올바르지 않은 포맷의 아이디 입니다.");
        }
    }

    private void validatePassword(final String password) {
        if (!passwordPattern.matcher(password).matches()) {
            throw new InvalidInputException("올바르지 않은 포맷의 패스워드 입니다.");
        }
    }
}
