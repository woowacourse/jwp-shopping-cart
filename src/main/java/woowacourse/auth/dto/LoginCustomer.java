package woowacourse.auth.dto;

public class LoginCustomer {

    private String loginId;

    public LoginCustomer(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }
}
