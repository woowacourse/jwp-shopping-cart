package woowacourse.auth.domain;

public class LoginCustomer {
    private String userName;

    public LoginCustomer() {
    }

    public LoginCustomer(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isUnauthorized() {
        return userName == null;
    }
}
