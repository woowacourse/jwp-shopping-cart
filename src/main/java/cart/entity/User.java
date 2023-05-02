package cart.entity;

public class User {

    private final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private final int MIN_PASSWORD_LENGTH = 5;

    private final String email;
    private final String password;

    public User(final String email, final String password) {
        validateFields(email, password);
        this.email = email;
        this.password = password;
    }

    private void validateFields(final String email, final String password) {
        validateEmail(email);
        validatePassword(password);
    }

    private void validateEmail(final String email) {
        if (!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("email이 형식에 맞지 않습니다. 입력된 값 : " + email);
        }
    }

    private void validatePassword(final String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("password가 5글자 미만입니다.");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
