package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidPasswordException;

public class Customer {

    private final Long id;
    private final String username;
    private final String email;
    private final String password;

    public Customer(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Customer(String username, String email, String password) {
        this(null, username, email, password);
    }

    public void validatePassword(String password){
        if (!this.password.equals(password)) {
            throw new InvalidPasswordException();
        }
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
}
