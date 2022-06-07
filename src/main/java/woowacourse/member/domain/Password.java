package woowacourse.member.domain;

import java.util.regex.Pattern;
import woowacourse.member.exception.PasswordChangeException;
import woowacourse.member.exception.PasswordNotValidException;
import woowacourse.member.infrastructure.PasswordEncoder;

public class Password {

    private static final int ENCODED_PASSWORD_LENGTH = 64;
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!?-])[A-Za-z\\d@!?-]{6,20}";

    private String password;

    private Password(String password) {
        this.password = password;
    }

    public static Password fromEncoded(String password) {
        validateEncoded(password);
        return new Password(password);
    }

    private static void validateEncoded(String password) {
        if (password.length() != ENCODED_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 올바른 비밀번호 입력 값이 아닙니다.");
        }
    }

    public static Password fromNotEncoded(String password, PasswordEncoder passwordEncoder) {
        validateNotEncoded(password);
        validatePassword(password);
        return new Password(passwordEncoder.encode(password));
    }

    private static void validateNotEncoded(String password) {
        if (password.length() == ENCODED_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 올바른 비밀번호 입력 값이 아닙니다.");
        }
    }

    private static void validatePassword(final String password) {
        if (!Pattern.matches(PASSWORD_REGEX, password)) {
            throw new PasswordNotValidException();
        }
    }

    public void updatePassword(final String oldPassword,
                               final String newPassword,
                               final PasswordEncoder passwordEncoder) {
        validateOldPassword(passwordEncoder.encode(oldPassword));
        validatePassword(newPassword);
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        validateSamePassword(encodedNewPassword);
        password = encodedNewPassword;
    }

    private void validateOldPassword(final String encodedOldPassword) {
        if (!authenticate(encodedOldPassword)) {
            throw new PasswordChangeException();
        }
    }

    public boolean authenticate(final String password) {
        return password.equals(this.password);
    }

    private void validateSamePassword(final String newPassword) {
        if (password.equals(newPassword)) {
            throw new PasswordChangeException();
        }
    }

    public String getPassword() {
        return password;
    }
}
