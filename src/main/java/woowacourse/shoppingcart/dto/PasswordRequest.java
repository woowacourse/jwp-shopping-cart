package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import woowacourse.shoppingcart.domain.Password;

public class PasswordRequest {

    @NotBlank
    private String password;

    private PasswordRequest() {
    }

    public PasswordRequest(String password) {
        this.password = password;
    }

    public Password toPassword() {
        return new Password(password);
    }

    public String getPassword() {
        return password;
    }
}
