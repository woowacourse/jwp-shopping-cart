package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerSignUpRequest {

    private String username;
    private String password;
    private String phoneNumber;
    private String address;

    private CustomerSignUpRequest() {
    }

    public CustomerSignUpRequest(final String username, final String password, final String phoneNumber,
                                 final String address) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Customer toCustomer() {
        return Customer.Builder()
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
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
