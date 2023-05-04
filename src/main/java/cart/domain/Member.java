package cart.domain;

import cart.exception.InvalidMemberException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Member {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,12}$";

    private final Long id;
    private final String email;
    private final String password;

    public Member(final Long id, final String email, final String password) {
        validate(email, password);
        this.id = id;
        this.email = email;
        this.password = password;
    }

    private void validate(final String email, final String password) {
        validateEmail(email);
        validatePassword(password);
    }

    private void validateEmail(final String email) {
        if (email.isBlank()) {
            throw new InvalidMemberException("이메일은 공백을 입력할 수 없습니다.");
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidMemberException("유효하지 않은 이메일 형식 입니다.");
        }
    }

    private void validatePassword(final String password) {
        if (password.isBlank()) {
            throw new InvalidMemberException("패스워드는 공백을 입력할 수 없습니다.");
        }

        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new InvalidMemberException("유효하지 않은 패스워드 입니다. " +
                    "최소 하나의 문자와 숫자로 이루어진 8자리 이상 12자리 이하의 패스워드를 입력해주세요.");
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
}
