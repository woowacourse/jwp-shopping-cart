package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotEmpty;

public class LoginRequest {

    @NotEmpty(groups = Request.allProperties.class)
    private String loginId;
    @NotEmpty(groups = Request.allProperties.class)
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String loginId, String password) {
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
