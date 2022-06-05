package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.vo.Email;
import woowacourse.shoppingcart.domain.vo.Username;

public class Customer {

    private final Long id;
    private final Email email;
    private final String password;
    private final Username username;

    public Customer(final Long id, final String email, final String password, final String username) {
        this.id = id;
        this.email = Email.valueOf(email);
        this.password = password;
        this.username = Username.valueOf(username);
    }

    private Customer(final Long id, final Email email, final String password, final Username username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static Customer createWithoutId(final String email, final String password, final String username) {
        return new Customer(null, email, password, username);
    }

    public static Customer createWithoutPassword(final Long id, final String email, final String username) {
        return new Customer(id, email, null, username);
    }

    public static Customer createWithoutEmailAndPassword(final Long id, final String username) {
        return new Customer(id, Email.empty(), null, Username.valueOf(username));
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username.getValue();
    }
}
