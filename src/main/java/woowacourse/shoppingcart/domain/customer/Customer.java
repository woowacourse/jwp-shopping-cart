package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final String name;
    private final Email email;
    private final HashedPassword hashedPassword;

    private Customer(final Long id, final String name, final Email email, final HashedPassword hashedPassword) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public Customer(final Long id, final String name, final String email, final HashedPassword password) {
        this(id, name, new Email(email), password);
    }

    public Customer(final String name, final String email, final HashedPassword password) {
        this(null, name, new Email(email), password);
    }

    public Customer updateName(final String newName) {
        return new Customer(id, newName, email, hashedPassword);
    }

    public Customer updatePassword(final HashedPassword newPassword) {
        return new Customer(id, name, email, newPassword);
    }

    public boolean unMatchPasswordWith(final String password) {
        return !this.hashedPassword.isSameFrom(password);
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

    public HashedPassword getPassword() {
        return hashedPassword;
    }
}
