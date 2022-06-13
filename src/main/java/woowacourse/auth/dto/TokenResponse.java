package woowacourse.auth.dto;

public class TokenResponse {
    private String accessToken;
    private long userId;

    public TokenResponse() {
    }

    public TokenResponse(String accessToken, long userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", userId=" + userId +
                '}';
    }
}
