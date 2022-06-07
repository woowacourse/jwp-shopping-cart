package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final Name name;
    private final Password password;
    private final Email email;
    private final Address address;
    private final PhoneNumber phoneNumber;

    public Customer(String name, String password,
            String email, String address, String phoneNumber) {
        this(null, name, password, email, address, phoneNumber);
    }

    public Customer(Long id, String name, String password, String email, String address, String phoneNumber) {
        this(id,
                new Name(name),
                new Password(password),
                new Email(email),
                new Address(address),
                new PhoneNumber(phoneNumber)
        );
    }

    public Customer(Long id, Name name, Password password, Email email, Address address,
            PhoneNumber phoneNumber) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Customer update(String address, String phoneNumber) {
        return new Customer(id, name, password, email, new Address(address), new PhoneNumber(phoneNumber));
    }

    public boolean isPasswordMatch(String password) {
        return this.password.isMatch(password);
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
