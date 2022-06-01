package woowacourse.auth.dto;

public class TokenRequest {

    private String userName;
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(final String userName, final String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
