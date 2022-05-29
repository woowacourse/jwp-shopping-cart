package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerSaveRequest {

    private String username;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;

    private CustomerSaveRequest() {
    }

    public CustomerSaveRequest(String username, String email, String password, String address,
            String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Customer toCustomer() {
        return new Customer(username, email, password, address, phoneNumber);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
