package woowacourse.shoppingcart.dto;

public class CustomerRequest {
    private String username;
    private String password;
    private String phoneNumber;
    private String address;

    public CustomerRequest(String username, String password, String phoneNumber, String address) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
