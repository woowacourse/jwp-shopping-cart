package woowacourse.shoppingcart.dto;

public class CustomerRequest {

    private final String email;
    private final String password;
    private final String name;
    private final String phone;
    private final String address;

    public CustomerRequest(final String email, final String password, final String name, final String phone, final String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
}
