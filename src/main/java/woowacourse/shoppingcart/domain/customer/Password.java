package woowacourse.shoppingcart.domain.customer;

import woowacourse.auth.utils.Encryptor;

public class Password {

    private static final String PASSWORD_FORMAT = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";

    private final String password;

    private Password(String password) {
        this.password = password;
    }

    public static Password of(String password) {
        validatePassword(password);
        return new Password(Encryptor.encrypt(password));
    }

    public static Password ofEncrypted(String password) {
        return new Password(password);
    }

    private static void validatePassword(String password) {
        if (password.isBlank() || !password.matches(PASSWORD_FORMAT)) {
            throw new IllegalArgumentException("올바르지 않은 비밀번호 형식입니다.");
        }
    }

    public boolean isSame(Password it) {
        return password.equals(it.password);
    }

    public String getValue() {
        return password;
    }
}
