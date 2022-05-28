package woowacourse.shoppingcart.dto;

public class CustomerResponse {

    private String loginId;
    private String username;

    public CustomerResponse() {}

    public CustomerResponse(String loginId, String username) {
        this.loginId = loginId;
        this.username = username;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getUsername() {
        return username;
    }
}
