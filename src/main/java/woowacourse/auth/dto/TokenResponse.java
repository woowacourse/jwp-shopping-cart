package woowacourse.auth.dto;

public class TokenResponse {

    private Long userId;
    private String accessToken;

    public TokenResponse() {
    }

    public TokenResponse(Long customerId, String accessToken) {
        this.userId = customerId;
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getUserId() {
        return userId;
    }
}
