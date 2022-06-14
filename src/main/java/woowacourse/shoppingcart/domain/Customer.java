package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidEmailException;

public class Customer {

    private final Long id;
    private final Username username;
    private final Email email;
    private final Password password;

    public Customer(Long id, Username username, Email email, Password password) {
        validateNull(username, email, password);
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    private void validateNull(Username username, Email email, Password password) {
        if (username == null || email == null || password == null) {
            throw new InvalidEmailException();
        }
    }

    public Customer(Username username, Email email, Password password) {
        this(null, username, email, password);
    }

    public Customer(String username, String email, String password) {
        this(null, new Username(username), new Email(email), new Password(password));
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
