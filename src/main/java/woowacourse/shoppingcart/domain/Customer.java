package woowacourse.shoppingcart.domain;

import woowacourse.auth.support.EmailValidator;
import woowacourse.auth.support.PasswordValidator;
import woowacourse.shoppingcart.exception.InvalidEmailException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

public class Customer {

    private static final int MAX_USERNAME_LENGTH = 32;

    private final Long id;
    private final String username;
    private final String email;
    private final String password;

    public Customer(Long id, String username, String email, String password) {
        validateUsername(username);
        validateEmail(email);
        validatePassword(password);

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Customer(String username, String email, String password) {
        this(null, username, email, password);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private void validateUsername(String username) {
        if (username == null || username.isBlank() || username.length() > MAX_USERNAME_LENGTH) {
            throw new InvalidEmailException();
        }
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
