package cart.domain;

import cart.exception.LengthException;

public class Member {

    private static final int NAME_MIN_LENGTH = 1;
    private static final int NAME_MAX_LENGTH = 5;
    private static final int PASSWORD_MIN_LENGTH = 4;
    private static final int PASSWORD_MAX_LENGTH = 12;

    private static final String EMAIL_ERROR_MESSAGE = "이메일은 빈 값이 될 수 없습니다.";
    private static final String PASSWORD_ERROR_MESSAGE = "비밀번호는 최소 4자 최대 12자까지 가능합니다.";
    private static final String NAME_ERROR_MESSAGE = "이름의 길이는 최소 2자 최대 5자까지 가능합니다.";

    private final String email;
    private final String password;
    private final String name;

    public Member(String email, String password, String name) {
        validateEmail(email);
        validatePassword(password);
        validateName(name);
        this.email = email;
        this.password = password;
        this.name = name;
    }

    private void validateEmail(String email) {
        if (email.isBlank()) {
            throw new LengthException(EMAIL_ERROR_MESSAGE);
        }
    }

    private void validatePassword(String password) {
        int length = password.length();
        if (password.isBlank() || length < PASSWORD_MIN_LENGTH || length > PASSWORD_MAX_LENGTH) {
            throw new LengthException(PASSWORD_ERROR_MESSAGE);
        }
    }

    private void validateName(String name) {
        int length = name.length();
        if (name.isBlank() || length < NAME_MIN_LENGTH || length > NAME_MAX_LENGTH) {
            throw new LengthException(NAME_ERROR_MESSAGE);
        }
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
