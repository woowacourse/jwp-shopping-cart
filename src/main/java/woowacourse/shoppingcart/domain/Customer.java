package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidPasswordException;

public class Customer {

    private final Long id;
    private final Username username;
    private final Email email;
    private final Password password;

    public Customer(Long id, Username username, Email email, Password password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Customer(Username username, Email email, Password password) {
        this(null, username, email, password);
    }

    public Customer(String username, String email, String password) {
        this(null, new Username(username), new Email(email), new Password(password));
    }

    public void validatePassword(Password password) {
        if (!this.password.equals(password)) {
            throw new InvalidPasswordException();
        }
    }

    public Long getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }
}
