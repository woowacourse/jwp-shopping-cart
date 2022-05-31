package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final Username username;
    private final Password password;
    private PhoneNumber phoneNumber;
    private String address;

    public Customer(Long id, Username username, Password password, PhoneNumber phoneNumber, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static Customer of(String username, String password, String phoneNumber, String address) {
        return new Customer(
            null,
            new Username(username),
            new Password(password),
            new PhoneNumber(phoneNumber),
            address);
    }

    public static Customer of(Long id, String username, String password, String phoneNumber, String address) {
        return new Customer(
            id,
            new Username(username),
            new Password(password),
            new PhoneNumber(phoneNumber),
            address);
    }

    public void matchPassword(String password) {
        this.password.match(password);
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = this.phoneNumber.update(phoneNumber);
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
