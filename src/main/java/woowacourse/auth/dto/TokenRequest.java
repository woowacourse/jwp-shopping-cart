package woowacourse.auth.dto;

public class TokenRequest {
    private String account;
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }
}
