package cart.domain.member;

public class Password {

    private final String password;

    public Password(final String password) {
        validate(password);
        this.password = password;
    }

    private void validate(String password) {
        if (password.length() < 4) {
            throw new IllegalArgumentException("비밀번호는 4자 이상이어야합니다.");
        }
    }

    public String getPassword() {
        return password;
    }
}
