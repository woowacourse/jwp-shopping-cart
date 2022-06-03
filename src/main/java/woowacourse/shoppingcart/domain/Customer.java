package woowacourse.shoppingcart.domain;

public class Customer {

    private final Long id;
    private final String name;
    private final Email email;
    private final Password password;

    public Customer(final Long id, final String name, final Email email, final Password password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Customer(final String name, final Email email, final Password password) {
        this(null, name, email, password);
    }

    public Customer updateName(final String newName) {
        return new Customer(id, newName, email, password);
    }

    public Customer updatePassword(final Password newPassword) {
        return new Customer(id, name, email, newPassword);
    }

    public boolean isSamePassword(final String password) {
        return this.password.isSamePassword(password);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }
}
