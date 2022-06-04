package woowacourse.shoppingcart.domain.customer;

import woowacourse.auth.domain.PasswordMatcher;

public class Customer {

    private final Long id;
    private final UserName name;
    private final Password password;
    private final Email email;
    private final Address address;
    private final PhoneNumber phoneNumber;

    public Customer(Long id, UserName name, Password password, Email email, Address address,
        PhoneNumber phoneNumber) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    private Customer(Long id, String name, Password password, String email, String address, String phoneNumber) {
        this(id,
            new UserName(name),
            password,
            new Email(email),
            new Address(address),
            new PhoneNumber(phoneNumber)
        );
    }

    public static Customer fromSaved(Long id, String name, String password, String email, String address,
        String phoneNumber) {
        return new Customer(id, name, Password.fromEncoded(password), email, address, phoneNumber);
    }

    public static Customer fromInput(String name, String password, String email, String address, String phoneNumber) {
        return new Customer(null, name, Password.fromInput(password), email, address, phoneNumber);
    }

    public Customer update(String address, String phoneNumber) {
        return new Customer(id, name, password, email, new Address(address), new PhoneNumber(phoneNumber));
    }

    public Customer encryptPassword(PasswordEncryptor encryptor) {
        return new Customer(id, name, password.encrypt(encryptor), email, address, phoneNumber);
    }

    public boolean isPasswordMatch(String password, PasswordMatcher matcher) {
        return this.password.isMatch(password, matcher);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getAddress() {
        return address.getValue();
    }

    public String getPhoneNumber() {
        return phoneNumber.getValue();
    }
}
