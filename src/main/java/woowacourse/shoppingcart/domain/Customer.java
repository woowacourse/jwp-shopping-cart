package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidCustomerException;

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

    public void isValidPassword(String password) {
        if (!this.password.equals(password)) {
                throw new InvalidCustomerException("로그인 실패");
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
