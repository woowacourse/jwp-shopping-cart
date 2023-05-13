package cart.entity.member;

import cart.exception.common.NullOrBlankException;

public class Password {

    private final String password;

    public Password(final String password) {
        validateNullOrBlank(password);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    private void validateNullOrBlank(String password) {
        if (password == null || password.isBlank()) {
            throw new NullOrBlankException();
        }
    }
}
