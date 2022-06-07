package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerResponse {

    private final String username;
    private final String email;
    private final String address;
    private final String phoneNumber;

    private CustomerResponse() {
        this(null, null, null, null);
    }

    public CustomerResponse(String username, String email, String address, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
            customer.getName(),
            customer.getEmail(),
            customer.getAddress(),
            customer.getPhoneNumber());
    }

    public String getUsername() {
        return username;
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
