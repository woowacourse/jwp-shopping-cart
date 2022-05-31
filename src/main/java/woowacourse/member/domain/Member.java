package woowacourse.member.domain;

import java.util.regex.Pattern;
import woowacourse.member.exception.EmailNotValidException;
import woowacourse.member.exception.NameNotValidException;
import woowacourse.member.exception.PasswordChangeException;
import woowacourse.member.exception.PasswordNotValidException;
import woowacourse.member.exception.WrongPasswordException;
import woowacourse.member.infrastructure.PasswordEncoder;

public class Member {

    private final String EMAIL_REGEX = "^(.+)@(.+)$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!?-])[A-Za-z\\d@!?-]{6,20}";

    private Long id;
    private String email;
    private String password;
    private String name;

    public Member(Long id, String email, String password, String name) {
        validateRightEmail(email);
        validateRightName(name);
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static Member from(String email, String password, String name, PasswordEncoder passwordEncoder) {
        return new Member(null, email, passwordEncoder.encode(password), name);
    }

    private void validateRightEmail(final String email) {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new EmailNotValidException();
        }
    }

    private void validateRightName(final String name) {
        if (name.length() < 1 || name.length() > 10) {
            throw new NameNotValidException();
        }
    }

    public void validateWrongPassword(final String oldPassword, final PasswordEncoder passwordEncoder) {
        if(!authenticate(passwordEncoder.encode(oldPassword))) {
            throw new WrongPasswordException();
        }
    }

    private boolean authenticate(final String password) {
        return password.equals(this.password);
    }

    public void updateName(final String name) {
        validateRightName(name);
        this.name = name;
    }

    public void updatePassword(final String oldPassword,
                               final String newPassword,
                               final PasswordEncoder passwordEncoder) {
        validateWrongPassword(oldPassword, passwordEncoder);
        String encodeNewPassword = passwordEncoder.encode(newPassword);
        validateOldSameNewPassword(password, encodeNewPassword);
        password = encodeNewPassword;
    }

    private void validateOldSameNewPassword(final String oldPassword, final String newPassword) {
        if (oldPassword.equals(newPassword)) {
            throw new PasswordChangeException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
