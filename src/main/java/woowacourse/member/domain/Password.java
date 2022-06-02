package woowacourse.member.domain;

import java.util.regex.Pattern;
import woowacourse.member.exception.PasswordChangeException;
import woowacourse.member.exception.PasswordNotValidException;
import woowacourse.member.infrastructure.PasswordEncoder;

public class Password {

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!?-])[A-Za-z\\d@!?-]{6,20}";

    private String password;

    public Password(String password) {
        this.password = password;
    }

    public Password(String password, PasswordEncoder passwordEncoder) {
        validatePassword(password);
        this.password = passwordEncoder.encode(password);
    }

    private void validatePassword(final String password) {
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
