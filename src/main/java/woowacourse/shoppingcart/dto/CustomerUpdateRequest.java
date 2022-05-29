package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerUpdateRequest {

    private String password;
    private String phoneNumber;
    private String address;

    private CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(final String password, final String phoneNumber, final String address) {
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Customer toCustomerWithUsername(final String username) {
        return Customer.builder()
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
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
