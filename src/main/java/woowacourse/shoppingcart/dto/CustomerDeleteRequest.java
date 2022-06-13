package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.PlainPassword;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerDeleteRequest {

    private static final String INVALID_PASSWORD_LENGTH_MESSAGE = "비밀번호는 8자 이상이어야 합니다.";

    @NotBlank(message = INVALID_PASSWORD_LENGTH_MESSAGE)
    @Size(min = PlainPassword.MIN_RAW_VALUE_LENGTH, message = INVALID_PASSWORD_LENGTH_MESSAGE)
    private String password;

    private CustomerDeleteRequest() {
    }

    public CustomerDeleteRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
