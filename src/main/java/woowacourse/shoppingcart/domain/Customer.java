package woowacourse.shoppingcart.domain;

public class Customer {
    private final Long id;
    private final String email;
    private final String name;
    private final String phone;
    private final String address;
    private final String password;

    public Customer(Long id, String email, String name, String phone, String address, String password) {
        CustomerInformationValidator.validateEmail(email);
        CustomerInformationValidator.validateName(name);
        CustomerInformationValidator.validatePhoneNumber(phone);
        CustomerInformationValidator.validateAddress(address);
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public Customer(String email, String name, String phone, String address, String password) {
        this(null, email, name, phone, address, password);
    }

    public Customer(Long id, Customer customer) {
        this(id, customer.email, customer.name, customer.phone, customer.address, customer.password);
    }

    public boolean checkPassword(String password) {
        return password.equals(this.password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }
}
