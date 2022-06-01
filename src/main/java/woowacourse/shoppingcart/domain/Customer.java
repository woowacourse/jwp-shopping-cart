package woowacourse.shoppingcart.domain;

public class Customer {

    private final Long id;
    private final String name;
    private final String email;
    private final String password;

    public Customer(final Long id, final String name, final String email, final String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Customer(final String name, final String email, final String password) {
        this(null, name, email, password);
    }

    public Customer updateName(final String newName) {
        return new Customer(id, newName, email, password);
    }

    public Customer updatePassword(final String newPassword) {
        return new Customer(id, name, email, newPassword);
    }

    public boolean isSamePassword(final String password) {
        return this.password.equals(password);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
