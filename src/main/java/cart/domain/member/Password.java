package cart.domain.member;

public class Password {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

    private final String password;

    public Password(final String password) {
        validate(password);
        this.password = password;
    }

    private void validate(final String password) {
        if (password.isBlank()) {
            throw new IllegalArgumentException("패스워드는 특수문자, 영문, 숫자를 모두 포함하여 8자 이상으로 작성해주세요.");
        }

        if (!password.matches(PASSWORD_REGEX)) {
            throw new IllegalArgumentException("패스워드는 특수문자, 영문, 숫자를 모두 포함하여 8자 이상으로 작성해주세요.");
        }
    }

    public String getPassword() {
        return password;
    }
}
