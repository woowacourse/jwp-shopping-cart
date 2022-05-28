package woowacourse.auth.dto;

public class TokenRequest {
    private String loginId;
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }
}
