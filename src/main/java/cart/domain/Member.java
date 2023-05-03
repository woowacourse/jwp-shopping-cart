package cart.domain;

public class Member {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private static final int PASSWORD_MIN_LENGTH = 4;

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
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new IllegalArgumentException("비밀번호의 길이는 4 이상이어야 합니다.");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
