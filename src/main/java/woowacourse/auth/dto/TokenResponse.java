package woowacourse.auth.dto;

public class TokenResponse {
    private String accessToken;
    private int userId;

    public TokenResponse() {
    }

    public TokenResponse(String accessToken, int userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public int getUserId() {
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
