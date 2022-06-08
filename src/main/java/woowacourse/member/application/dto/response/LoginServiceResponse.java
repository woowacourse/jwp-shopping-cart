package woowacourse.member.application.dto.response;

public class LoginServiceResponse {

    private final String token;
    private final String nickname;

    public LoginServiceResponse(String token, String nickname) {
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
