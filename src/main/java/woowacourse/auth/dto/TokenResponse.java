package woowacourse.auth.dto;

public class TokenResponse {

    private String nickname;
    private String accessToken;

    public TokenResponse() {
    }

    public TokenResponse(String nickname, String accessToken) {
        this.nickname = nickname;
        this.accessToken = accessToken;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
