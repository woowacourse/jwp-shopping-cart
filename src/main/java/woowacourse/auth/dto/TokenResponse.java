package woowacourse.auth.dto;

import woowacourse.shoppingcart.domain.customer.CustomerToken;

public class TokenResponse {

    private String nickname;
    private String accessToken;

    private TokenResponse() {
    }

    private TokenResponse(String nickname, String accessToken) {
        this.nickname = nickname;
        this.accessToken = accessToken;
    }

    public TokenResponse(CustomerToken customerToken) {
        this(customerToken.getNickname(), customerToken.getAccessToken());
    }

    public String getNickname() {
        return nickname;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
