package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class Customer {

    private final Long id;
    private final Username username;
    private final Email email;
    private final Password password;
    private Address address;
    private PhoneNumber phoneNumber;

    public Customer(Long id, Customer customer) {
        this(id, new Username(customer.getUsername()),
                new Email(customer.getEmail()),
                new Password(customer.getPassword()),
                new Address(customer.getAddress()),
                new PhoneNumber(customer.getPhoneNumber()));
    }

    public Customer(String username, String email, String password, String address, String phoneNumber) {
        this(null, new Username(username), new Email(email), new Password(password), new Address(address),
                new PhoneNumber(phoneNumber));
    }

    public Customer(Long id, String username, String email, String password, String address, String phoneNumber) {
        this(id, new Username(username), new Email(email), new Password(password), new Address(address),
                new PhoneNumber(phoneNumber));
    }

    public Customer(Long id, Username username, Email email, Password password, Address address,
            PhoneNumber phoneNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void modify(String address, String phoneNumber) {
        this.address = new Address(address);
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public String getAddress() {
        return address.getValue();
    }

    public String getPhoneNumber() {
        return phoneNumber.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer)o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
