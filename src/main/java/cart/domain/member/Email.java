package cart.domain.member;

public class Email {

    private static final int MAX_EMAIL_LENGTH = 254;
    public final String email;

    public Email(final String email) {
        validateEmail(email);
        this.email = email;
    }

    private void validateEmail(final String email) {
        validateBlank(email);
        validateLength(email);
    }

    private void validateBlank(final String email) {
        if (email.isBlank()) {
            throw new IllegalArgumentException("Email 은 공백일 수 없습니다.");
        }
    }

    private void validateLength(final String email) {
        if (email.length() > MAX_EMAIL_LENGTH) {
            throw new IllegalArgumentException("Email 은 254자를 넘을 수 없습니다.");
        }
    }

    public String email() {
        return email;
    }
}
