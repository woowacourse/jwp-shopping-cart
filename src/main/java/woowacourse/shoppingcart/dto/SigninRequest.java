package woowacourse.shoppingcart.dto;

public class SigninRequest {

    private String account;
    private String password;

    private SigninRequest() {
    }

    public SigninRequest(String account, String password) {
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
