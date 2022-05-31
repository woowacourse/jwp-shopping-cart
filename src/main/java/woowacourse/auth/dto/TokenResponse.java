package woowacourse.auth.dto;

public class TokenResponse {

    private String userName;
    private String accessToken;

    public TokenResponse() {
    }

    public TokenResponse(final String userName, final String accessToken) {
        this.userName = userName;
        this.accessToken = accessToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
