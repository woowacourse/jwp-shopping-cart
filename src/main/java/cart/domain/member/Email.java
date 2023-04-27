package cart.domain.member;

public class Email {

    private static final String EMAIL_REGEX = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

    private final String email;

    public Email(final String email) {
        validate(email);
        this.email = email;
    }

    private void validate(final String email) {
        if (email.isBlank()) {
            throw new IllegalArgumentException("이메일의 형식이 맞지 않습니다. 예시) example@example.com");
        }

        if (!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("이메일의 형식이 맞지 않습니다. 예시) example@example.com");
        }
    }
}
