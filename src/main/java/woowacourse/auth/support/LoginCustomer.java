package woowacourse.auth.support;

public class LoginCustomer {

    private final String email;

    public LoginCustomer(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
