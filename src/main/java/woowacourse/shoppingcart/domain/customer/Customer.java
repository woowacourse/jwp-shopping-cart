package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

import woowacourse.shoppingcart.domain.customer.password.EncodedPassword;
import woowacourse.shoppingcart.domain.customer.password.PasswordEncoder;
import woowacourse.shoppingcart.domain.customer.password.RawPassword;

public class Customer {

    private final Long id;
    private final Username username;
    private final Email email;
    private final EncodedPassword password;
    private Address address;
    private PhoneNumber phoneNumber;

    public Customer(Long id, Customer customer) {
        this(id, customer.username, customer.email, customer.password, customer.address, customer.phoneNumber);
    }

    public Customer(String username, String email, EncodedPassword password, String address, String phoneNumber) {
        this(null, new Username(username), new Email(email), password, new Address(address),
                new PhoneNumber(phoneNumber));
    }

    public Customer(Long id, String username, String email, EncodedPassword password, String address,
            String phoneNumber) {
        this(id, new Username(username), new Email(email), password, new Address(address),
                new PhoneNumber(phoneNumber));
    }

    public Customer(Long id, Username username, Email email, EncodedPassword password, Address address,
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

    public EncodedPassword getPassword() {
        return password;
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

    public boolean matchPassword(RawPassword rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, this.password);
    }
}
