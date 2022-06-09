package woowacourse.auth.dto;

public class CustomerRequest {

    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;

    private CustomerRequest() {
    }

    public CustomerRequest(final String email, final String password, final String name, final String phone,
                           final String address) {
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
