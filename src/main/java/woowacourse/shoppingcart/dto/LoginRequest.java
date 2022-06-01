package woowacourse.shoppingcart.dto;

public class LoginRequest {

    private String userId;
    private String password;

    private LoginRequest() {
    }

    public LoginRequest(final String userId, final String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
