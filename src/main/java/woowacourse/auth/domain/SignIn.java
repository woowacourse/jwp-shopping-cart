package woowacourse.auth.domain;

public class SignIn {

    private final String email;
    private final String password;

    public SignIn(String email, String password) {
        validateEmail(email);
        validatePassword(password);

        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private void validateEmail(String email) {
        new UserEmail(email);
    }

    private void validatePassword(String password) {
        new UserPassword(password);
    }
}
