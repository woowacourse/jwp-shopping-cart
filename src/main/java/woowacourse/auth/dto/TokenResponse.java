package woowacourse.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TokenResponse {

    private String accessToken;
    private Long expirationTime;
    private CustomerResponse customer;

    public static TokenResponse from(String accessToken, Long expirationTime, CustomerResponse customer) {
        return new TokenResponse(accessToken, expirationTime, customer);
    }
}
