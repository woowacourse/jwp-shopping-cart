package woowacourse.customer.domain;

import woowacourse.customer.support.passwordencoder.PasswordEncoder;

public class Customer {

    private final Long id;
    private final Username username;
    private EncodedPassword password;
    private PhoneNumber phoneNumber;
    private String address;

    public Customer(final Long id, final Username username, final EncodedPassword password, final PhoneNumber phoneNumber, final String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static Customer of(final String username, final String password, final String phoneNumber, final String address) {
        return new Customer(
            null,
            new Username(username),
            new EncodedPassword(password),
            new PhoneNumber(phoneNumber),
            address);
    }

    public static Customer of(final Long id, final String username, final String password, final String phoneNumber, final String address) {
        return new Customer(
            id,
            new Username(username),
            new EncodedPassword(password),
            new PhoneNumber(phoneNumber),
            address
        );
    }

    public void matchesPassword(final PasswordEncoder passwordEncoder, final String password) {
        this.password.matches(passwordEncoder, password);
    }

    public void updatePhoneNumber(final String phoneNumber) {
        if (!phoneNumber.isBlank()) {
            this.phoneNumber = this.phoneNumber.update(phoneNumber);
        }
    }

    public void updateAddress(final String address) {
        if (!address.isBlank()) {
            this.address = address;
        }
    }

    public void updatePassword(final String password) {
        this.password = this.password.update(password);
    }

    public Long getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public EncodedPassword getPassword() {
        return password;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
