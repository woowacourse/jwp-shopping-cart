package woowacourse.auth.dto;

public class TokenResponse {
    private String accessToken;
    private String name;

    public TokenResponse() {
    }

    public TokenResponse(String accessToken, String name) {
        this.accessToken = accessToken;
        this.name = name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getName() {
        return name;
    }
}
