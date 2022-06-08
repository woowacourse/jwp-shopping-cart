package woowacourse.auth.dto;

import woowacourse.shoppingcart.domain.customer.CustomerToken;

public class TokenResponse {

    private String nickname;
    private String token;

    private TokenResponse() {
    }

    private TokenResponse(String nickname, String token) {
        this.nickname = nickname;
        this.token = token;
    }

    public TokenResponse(CustomerToken customerToken) {
        this(customerToken.getNickname(), customerToken.getAccessToken());
    }

    public String getNickname() {
        return nickname;
    }

    public String getToken() {
        return token;
    }
}
