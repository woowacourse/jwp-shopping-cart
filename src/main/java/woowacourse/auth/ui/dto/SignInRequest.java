package woowacourse.auth.ui.dto;

public class SignInRequest {

    private String account;
    private String password;

    private SignInRequest() {
    }

    public SignInRequest(String account, String password) {
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
