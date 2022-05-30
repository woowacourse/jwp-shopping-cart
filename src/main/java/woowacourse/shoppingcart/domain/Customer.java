package woowacourse.shoppingcart.domain;

public class Customer {

    private final String username;
    private final String password;

    public Customer(final String username, final String password) {
        validateUsername(username);
        validatePassword(password);
        this.username = username;
        this.password = password;
    }

    private void validateUsername(final String username) {
        validateUsernameEmpty(username);
    }

    private void validatePassword(final String password) {
        validatePasswordEmpty(password);
    }

    private void validateUsernameEmpty(final String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("사용자 이름을 입력해주세요.");
        }
    }

    private void validatePasswordEmpty(final String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
    }
}
