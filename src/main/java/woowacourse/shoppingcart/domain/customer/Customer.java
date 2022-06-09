package woowacourse.shoppingcart.domain.customer;

public class Customer {
    public static final Long GUEST = -1L;
    private final CustomerId id;
    private final Email email;
    private final Name name;
    private final Phone phone;
    private final Address address;
    private final Password password;

    public Customer(final CustomerId id, final Email email, final Name name, final Phone phone, final Address address, final Password password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public Customer(final Email email, final Name name, final Phone phone, final Address address, final Password password) {
        this(null, email, name, phone, address, password);
    }

    public Customer(final CustomerId id, final Customer customer) {
        this(id, customer.email, customer.name, customer.phone, customer.address, customer.password);
    }

    public boolean isSame(final Password password) {
        return this.password.isSame(password);
    }

    public CustomerId getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public Password getPassword() {
        return password;
    }
}
