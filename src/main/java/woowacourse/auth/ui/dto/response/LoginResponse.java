package woowacourse.auth.ui.dto.response;

import woowacourse.auth.application.dto.response.LoginServiceResponse;

public class LoginResponse {

    private String token;
    private String nickname;

    public LoginResponse() {
    }

    public LoginResponse(LoginServiceResponse loginServiceResponse) {
        this.token = loginServiceResponse.getToken();
        this.nickname = loginServiceResponse.getNickname();
    }

    public String getToken() {
        return token;
    }

    public String getNickname() {
        return nickname;
    }
}
