package cart.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean isValidLogin(final String email, final String password) {
        final String validEmail = "a@a.com";
        final String validPassword = "password1";
        return validEmail.equals(email) && validPassword.equals(password);
    }
}
