package woowacourse.auth.dto;

import woowacourse.shoppingcart.dto.CustomerDto;

public class SignInResponseDto {

    private final String accessToken;
    private final Long expirationTime;
    private final CustomerDto customer;

    public SignInResponseDto(final String accessToken,
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
