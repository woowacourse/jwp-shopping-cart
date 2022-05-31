package woowacourse.shoppingcart.dto;

public class CustomerRequest {

    private final String username;
    private final String password;
    private final String email;
    private final String address;
    private final String phoneNumber;

    private CustomerRequest() {
        this(null, null, null, null, null);
    }

    public CustomerRequest(String username, String password, String email, String address, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
