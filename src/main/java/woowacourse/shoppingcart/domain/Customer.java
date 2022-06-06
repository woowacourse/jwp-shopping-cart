package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.custum.InvalidInputException;

public class Customer {
    private static final Pattern usernamePattern =
            Pattern.compile("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    private static final Pattern nicknamePattern =
            Pattern.compile("^[a-zA-Z가-힣0-9]{2,10}$");

    private final Long id;
    private final String username;
    private final Password password;
    private final String nickname;

    public Customer(final Long id,
                    final String username,
                    final String password,
                    final String nickname) {
        validateUsername(username);
        validateNickname(nickname);
        this.id = id;
        this.username = username;
        this.password = new Password(password);
        this.nickname = nickname;
    }

    public static Customer ofNullId(final String username,
                                    final String password,
                                    final String nickname) {
        return new Customer(null, username, password, nickname);
    }

    private void validateUsername(final String username) {
        if (!usernamePattern.matcher(username).matches()) {
            throw new InvalidInputException("아이디");
        }
    }

    private void validateNickname(final String nickname) {
        if (!nicknamePattern.matcher(nickname).matches()) {
            throw new InvalidInputException("닉네임");
        }
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public String getNickname() {
        return nickname;
    }

}
