package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.domain.Password;

public class Customer {
    private final Name name;
    private final Email email;
    private final Password password;

    public Customer(String name, String email, String password) {
        this.name = new Name(name);
        this.email = new Email(email);
        this.password = new Password(password);
    }

    public boolean isSamePassword(String password) {
        return this.password.isSamePassword(password);
    }

    public String getUsername() {
        return name.get();
    }

    public String getEmail() {
        return email.get();
    }
}
