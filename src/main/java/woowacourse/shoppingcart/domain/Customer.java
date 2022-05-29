package woowacourse.shoppingcart.domain;

public class Customer {

    private final Name name;
    private final Password password;
    private final Email email;
    private final Address address;
    private final PhoneNumber phoneNumber;

    public Customer(String name, String password, String email, String address, String phoneNumber) {
        this(new Name(name),
                new Password(password),
                new Email(email),
                new Address(address),
                new PhoneNumber(phoneNumber)
        );
    }

    public Customer(Name name, Password password, Email email, Address address,
            PhoneNumber phoneNumber) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
