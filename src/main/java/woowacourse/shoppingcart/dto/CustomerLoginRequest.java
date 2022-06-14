package woowacourse.shoppingcart.dto;

public class CustomerLoginRequest {

    private String userId;
    private String password;

    public CustomerLoginRequest() {
    }

    public CustomerLoginRequest(final String userId, final String password) {
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
