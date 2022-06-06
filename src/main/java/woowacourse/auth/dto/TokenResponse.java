package woowacourse.auth.dto;

import woowacourse.shoppingcart.dto.customer.CustomerResponse;

public class TokenResponse {

    private String accessToken;
    private Long expirationTime;
    private CustomerResponse customer;

    private TokenResponse() {
    }

    public TokenResponse(String accessToken, Long expirationTime,
                         CustomerResponse customerResponse) {
        this.accessToken = accessToken;
        this.expirationTime = expirationTime;
        this.customer = customerResponse;
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
