package cart.domain.member;

public class Password {

    private static final int MAX_PASSWORD_LENGTH = 50;
    private final String password;

    public Password(String password) {
        this.password = validate(password);
    }

    private String validate(String password) {
        validateNotEmpty(password);
        validateLength(password);
        return password;
    }

    private void validateNotEmpty(String password) {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력하세요.");
        }
    }

    private void validateLength(String password) {
        int length = password.length();
        if (length > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("비밀번호는 " + MAX_PASSWORD_LENGTH + "자 이하여야 합니다. (현재 " + length + "자)");
        }
    }

    public String getPassword() {
        return password;
    }
}
