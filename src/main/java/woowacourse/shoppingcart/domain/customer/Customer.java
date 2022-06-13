package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Name name;
    private final Email email;
    private final Password password;

    public Customer(final String name, final String email, final String password) {
        this.name = new Name(name);
        this.email = new Email(email);
        this.password = new Password(password);
    }

    public boolean isSamePassword(final Password password) {
        return this.password.equals(password);
    }

    public String getUsername() {
        return name.get();
    }

    public String getEmail() {
        return email.get();
    }
}
