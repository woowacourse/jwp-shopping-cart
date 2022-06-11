package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CustomerAddRequest {

    @NotBlank
    @Email(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 아닙니다.")
    private String loginId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    private CustomerAddRequest() {
    }

    public CustomerAddRequest(String loginId, String name, String password) {
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
