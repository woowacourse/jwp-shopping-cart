package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Password;

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

    public Customer toCustomeWithPassword(final Password password) {
        return Customer.builder()
                .username(username)
                .password(password.getPassword())
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
