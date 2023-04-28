package cart.domain.user;

import cart.exception.user.UserFieldNotValidException;

public class UserPassword {

    private final String password;

    public UserPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    private void validatePassword(String password) {
        if (password.isBlank()) {
            throw new UserFieldNotValidException("비밀번호는 공백을 입력할 수 없습니다.");
        }
    }

    public String getPassword() {
        return password;
    }
}
