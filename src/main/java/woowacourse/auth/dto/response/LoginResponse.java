package woowacourse.auth.dto.response;

public class LoginResponse {

    private String token;
    private String nickname;

    private LoginResponse() {
    }

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
