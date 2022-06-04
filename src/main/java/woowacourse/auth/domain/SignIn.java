package woowacourse.auth.domain;

import woowacourse.auth.support.EmailValidator;
import woowacourse.auth.support.PasswordValidator;
import woowacourse.shoppingcart.exception.InvalidEmailException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

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
        EmailValidator emailValidator = new EmailValidator();
        if (!emailValidator.isValid(email)) {
            throw new InvalidEmailException();
        }
    }

    private void validatePassword(String password) {
        PasswordValidator passwordValidator = new PasswordValidator();
        if (!passwordValidator.isValid(password)) {
            throw new InvalidPasswordException();
        }
    }
}
