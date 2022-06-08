package woowacourse.auth.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class LoginTokenResponse {

    private String accessToken;
    private Long expirationTime;
    private Customer customer;

    private LoginTokenResponse() {
    }

    public LoginTokenResponse(String accessToken, Long expirationTime, Customer customer) {
        this.accessToken = accessToken;
        this.expirationTime = expirationTime;
        this.customer = customer;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public Customer getCustomer() {
        return customer;
    }
}
