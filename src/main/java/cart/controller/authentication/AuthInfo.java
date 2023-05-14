package cart.controller.authentication;

import cart.domain.Email;
import cart.domain.Password;

public class AuthInfo {

    private final Email email;
    private final Password password;

    public AuthInfo(final String email, final String password) {
        this.email = new Email(email);
        this.password = new Password(password);
    }

    public String getEmailValue() {
        return email.getAddress();
    }


    public String getPasswordValue() {
        return password.getValue();
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }
}
