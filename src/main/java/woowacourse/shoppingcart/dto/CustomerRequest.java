package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CustomerRequest {

    @Email(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 아닙니다.")
    private String loginId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    private CustomerRequest() {
    }

    public CustomerRequest(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
