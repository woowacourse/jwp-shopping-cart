package woowacourse.shoppingcart.domain;

import woowacourse.auth.domain.UserEmail;
import woowacourse.auth.domain.UserPassword;
import woowacourse.auth.domain.Username;
import woowacourse.shoppingcart.exception.ValidationException;

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
        new Username(username);
    }

    private void validateEmail(String email) {
        new UserEmail(email);
    }

    private void validatePassword(String password) {
        new UserPassword(password);
    }
}
