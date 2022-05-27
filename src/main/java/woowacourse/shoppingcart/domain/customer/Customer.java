package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final Username username;
    private final Password password;
    private final PhoneNumber phoneNumber;
    private final String address;

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
