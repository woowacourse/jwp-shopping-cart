package cart.domain.member;

public class Password {

    private static final int MIN_PASSWORD_LENGTH = 12;
    private final String password;

    public Password(final String password) {
        validatePassword(password);
        this.password = password;
    }

    private void validatePassword(final String password) {
        validateBlank(password);
        validateLength(password);
    }

    private void validateBlank(final String password) {
        if (password.isBlank()) {
            throw new IllegalArgumentException("Password 는 공백일 수 없습니다.");
        }
    }

    private void validateLength(final String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password 는 12자 이상이어야 합니다.");
        }
    }

    public String password() {
        return password;
    }
}
