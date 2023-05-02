package cart.domain;

public class Member {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private static final String PASSWORD_REGEX = "/^[a-zA-Z0-9]+$/";

    private final String email;
    private final String password;

    public Member(final String email, final String password) {
        validate(email, password);
        this.email = email;
        this.password = password;
    }

    private void validate(final String email, final String password) {
        if (!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("이메일 형식과 일치하지 않습니다.");
        }
        if (!password.matches(PASSWORD_REGEX)) {
            throw new IllegalArgumentException("비밀번호는 영어와 숫자를 포함해야 합니다.");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
