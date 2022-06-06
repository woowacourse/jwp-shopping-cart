package woowacourse.shoppingcart.dao.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerDto {

    private final String username;
    private final String email;
    private final String password;
    private final String address;
    private final String phoneNumber;

    public CustomerDto(Customer customer) {
        this(customer.getUsername(), customer.getEmail(), customer.getPassword().getValue(), customer.getAddress(),
                customer.getPhoneNumber());
    }

    public CustomerDto(String username, String email, String password, String address, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
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
