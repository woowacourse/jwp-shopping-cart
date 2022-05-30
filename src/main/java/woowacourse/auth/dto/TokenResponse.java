package woowacourse.auth.dto;

public class TokenResponse {

    private String accessToken;
    private Long expirationTime;

    private TokenResponse() {
    }

    public TokenResponse(String accessToken, Long expirationTime) {
        this.accessToken = accessToken;
        this.expirationTime = expirationTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }
}
