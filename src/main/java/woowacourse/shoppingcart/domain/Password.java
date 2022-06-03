package woowacourse.shoppingcart.domain;

public class Password {

    private static final int STANDARD_PASSWORD_MIN_LENGTH = 8;
    private static final int STANDARD_PASSWORD_MAX_LENGTH = 20;
    private static final String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*_-])[A-Za-z\\d@$!%*?&]{8,16}$";

    private final String password;

    public Password(String password) {
        validatePassword(password);
        this.password = password;
    }

    public void validatePassword(String password) {
        if (password.matches(regex)) {
            return;
        }
        throw new IllegalArgumentException("패스워드는 영문 소문자, 숫자, 특수문자 8~16자리로 구성하여야 합니다.");
    }

    public String value() {
        return password;
    }
}
