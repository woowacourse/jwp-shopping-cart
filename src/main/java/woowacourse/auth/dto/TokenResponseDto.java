package woowacourse.auth.dto;

import woowacourse.shoppingcart.dto.response.CustomerDto;

public class TokenResponseDto {

    private final String accessToken;
    private final Long expirationTime;
    private final CustomerDto customer;

    public TokenResponseDto(final String accessToken,
                            final Long expirationTime,
                            final CustomerDto customer) {
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

    public CustomerDto getCustomer() {
        return customer;
    }
}
