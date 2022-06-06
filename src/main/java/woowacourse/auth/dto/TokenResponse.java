package woowacourse.auth.dto;

public class TokenResponse {

    private Long customerId;
    private String accessToken;

    public TokenResponse() {
    }

    public TokenResponse(Long customerId, String accessToken) {
        this.customerId = customerId;
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
