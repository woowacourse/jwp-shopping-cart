package woowacourse.auth.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerDto {
    private String username;
    private String phoneNumber;
    private String address;

    public CustomerDto() {
    }

    public CustomerDto(String username, String phoneNumber, String address) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static CustomerDto from(Customer customer) {
        return new CustomerDto(
            customer.getUsername().getValue(),
            customer.getPhoneNumber().getValue(),
            customer.getAddress()
        );
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
