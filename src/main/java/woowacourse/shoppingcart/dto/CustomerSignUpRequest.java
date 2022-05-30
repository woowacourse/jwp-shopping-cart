package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerSignUpRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String phoneNumber;

    @NotBlank
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
        return Customer.builder()
                .username(username)
                .purePassword(password)
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
