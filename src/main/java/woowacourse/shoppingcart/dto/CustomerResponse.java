package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerResponse {

    private final String username;
    private final String phoneNumber;
    private final String address;

    public CustomerResponse(final String username, final String phoneNumber, final String address) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static CustomerResponse from(final Customer customer) {
        return new CustomerResponse(customer.getUsername(), customer.getPhoneNumber(), customer.getAddress());
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
