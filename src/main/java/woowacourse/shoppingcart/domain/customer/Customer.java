package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class Customer {

    private final Long id;
    private final Username username;
    private final Password password;
    private final PhoneNumber phoneNumber;
    private final Address address;

    public Customer(final Long id, final Username username, final Password password, final PhoneNumber phoneNumber,
                    final Address address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void matchPassword(final PasswordEncoder passwordEncoder, final PlainPassword plainPassword) {
        password.matchPassword(passwordEncoder, plainPassword);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username.getUsername();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public String getPhoneNumber() {
        return phoneNumber.getPhoneNumber();
    }

    public String getAddress() {
        return address.getAddress();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long id;
        private Username username;
        private Password password;
        private PhoneNumber phoneNumber;
        private Address address;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = new Username(username);
            return this;
        }

        public Builder password(String password) {
            this.password = new Password(password);
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = new PhoneNumber(phoneNumber);
            return this;
        }

        public Builder address(String address) {
            this.address = new Address(address);
            return this;
        }

        public Customer build() {
            return new Customer(id, username, password, phoneNumber, address);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
