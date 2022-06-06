package woowacourse.shoppingcart.auth.dto;

public class LoginResponse {

    private String accessToken;

    private LoginResponse() {
    }

    public LoginResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
