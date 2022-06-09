package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final Email email;
    private final Name name;
    private final Phone phone;
    private final Address address;
    private final Password password;

    public Customer(Long id, String email, String name, String phone, String address, Password password) {
        this.id = id;
        this.email = new Email(email);
        this.name = new Name(name);
        this.phone = new Phone(phone);
        this.address = new Address(address);
        this.password = password;
    }

    public Customer(String email, String name, String phone, String address, String password) {
        this(null, email, name, phone, address, Password.fromPlain(password));
    }

    public Customer(Long id, Customer customer) {
        this(id, customer.getEmail(), customer.getName(), customer.getPhone(), customer.getAddress(),
                Password.fromEncrypt(customer.getPassword()));
    }

    public boolean checkPassword(String password) {
        return this.password.matchPassword(password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getName() {
        return name.getValue();
    }

    public String getPhone() {
        return phone.getValue();
    }

    public String getAddress() {
        return address.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
