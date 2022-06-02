package woowacourse.shoppingcart.domain;

public class Password {

    private static final int STANDARD_PASSWORD_LENGTH = 8;

    private final String password;

    public Password(String password) {
        validatePassword(password);
        this.password = password;
    }

    public void validatePassword(String password) {
        if (password.length() < STANDARD_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("패스워드의 길이는 8자 이상이어야 합니다.");
        }
    }

    @Override
    public String toString() {
        return password.toString();
    }
}
