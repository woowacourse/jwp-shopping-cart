package woowacourse.auth.dto;

public class LoginResponse {

    private final String token;
    private final String nickname;

    public LoginResponse(String token, String nickname) {
        this.token = token;
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public String getNickname() {
        return nickname;
    }
}
