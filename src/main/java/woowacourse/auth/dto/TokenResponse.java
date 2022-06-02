package woowacourse.auth.dto;

import woowacourse.shoppingcart.domain.Customer;

public class TokenResponse {

    private String accessToken;
    private Long expirationTime;
    private Customer customer;

    private TokenResponse() {
    }

    public TokenResponse(String accessToken, Long expirationTime, Customer customer) {
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
