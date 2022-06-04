package woowacourse.auth.dto;

public class TokenResponse {
    private String accessToken;
    private int customerId;

    public TokenResponse() {
    }

    public TokenResponse(String accessToken, int customerId) {
        this.accessToken = accessToken;
        this.customerId = customerId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public int getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}
