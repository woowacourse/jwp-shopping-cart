package woowacourse.auth.dto;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;

public class LoginTokenResponse {

    private String accessToken;
    private Long expirationTime;
    private CustomerResponse customer;

    private LoginTokenResponse() {
    }

    public LoginTokenResponse(String accessToken, Long expirationTime, Customer customer) {
        this.accessToken = accessToken;
        this.expirationTime = expirationTime;
        this.customer = new CustomerResponse(customer);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }
}
