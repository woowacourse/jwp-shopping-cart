package woowacourse.auth.dto;

public class TokenResponse {

    private String nickname;
    private String token;

    public TokenResponse() {
    }

    public TokenResponse(String nickname, String token) {
        this.nickname = nickname;
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public String getToken() {
        return token;
    }
}
