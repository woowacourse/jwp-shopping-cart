package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

import woowacourse.shoppingcart.domain.customer.password.Password;
import woowacourse.shoppingcart.domain.customer.password.PasswordEncoder;

public class Customer {

    private final Long id;
    private final Username username;
    private final Email email;
    private final Password password;
    private Address address;
    private PhoneNumber phoneNumber;

    private Customer(Long id, Username username, Email email, Password password, Address address,
            PhoneNumber phoneNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static Customer createWithRawPassword(String username, String email, String password, String address,
            String phoneNumber) {
        return new Customer(null, new Username(username), new Email(email), Password.createRaw(password),
                new Address(address),
                new PhoneNumber(phoneNumber));
    }

    public static Customer createWithEncodedPassword(Long id, Customer customer) {
        return new Customer(id, new Username(customer.getUsername()),
                new Email(customer.getEmail()),
                Password.createEncoded(customer.getPassword()),
                new Address(customer.getAddress()),
                new PhoneNumber(customer.getPhoneNumber()));
    }

    public static Customer createWithEncodedPassword(Long id, String username, String email, String password,
            String address, String phoneNumber) {
        return new Customer(id, new Username(username), new Email(email), Password.createEncoded(password),
                new Address(address),
                new PhoneNumber(phoneNumber));
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password.encode(passwordEncoder);
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

    public boolean isPassword(String password) {
        return this.getPassword().equals(password);
    }
}
