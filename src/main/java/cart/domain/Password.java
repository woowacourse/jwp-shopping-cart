package cart.domain;

import cart.domain.exception.WrongPasswordFormatException;
import cart.domain.exception.WrongPasswordFormatException.Language;

public class Password {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;

    private final String password;

    public Password(final String password) {
        validate(password);
        this.password = password;
    }

    private void validate(final String password) {
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new WrongPasswordFormatException(Language.KO);
        }
    }

    public String getPassword() {
        return password;
    }
}
