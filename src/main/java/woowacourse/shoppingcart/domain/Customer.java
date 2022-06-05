package woowacourse.shoppingcart.domain;

public class Customer {

    private final Long id;
    private final String name;
    private final Email email;
    private final Password password;

    private Customer(final Long id, final String name, final Email email, final Password password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Customer(final  Long id, final String name, final String email, final Password password) {
        this(id, name, new Email(email), password);
    }

    public Customer(final String name, final String email, final Password password) {
        this(null, name, new Email(email), password);
    }

    public Customer updateName(final String newName) {
        return new Customer(id, newName, email, password);
    }

    public Customer updatePassword(final Password newPassword) {
        return new Customer(id, name, email, newPassword);
    }

    public boolean unMatchPasswordWith(final String password, final PasswordConvertor passwordConvertor) {
        return !this.password.isSamePassword(password, passwordConvertor);
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
