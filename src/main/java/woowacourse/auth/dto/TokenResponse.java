package woowacourse.auth.dto;

public class TokenResponse {
    private String accessToken;
    private String username;

    public TokenResponse() {
    }

    public TokenResponse(String accessToken, String username) {
        this.accessToken = accessToken;
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }
}
