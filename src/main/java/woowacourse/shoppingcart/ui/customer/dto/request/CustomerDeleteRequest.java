package woowacourse.shoppingcart.ui.customer.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import woowacourse.shoppingcart.application.dto.CustomerDeleteServiceRequest;
import woowacourse.shoppingcart.domain.Password;

public class CustomerDeleteRequest {

    private static final String INVALID_PASSWORD_LENGTH_MESSAGE = "비밀번호는 8자 이상이어야 합니다.";

    @NotBlank(message = INVALID_PASSWORD_LENGTH_MESSAGE)
    @Size(min = Password.MIN_RAW_VALUE_LENGTH, message = INVALID_PASSWORD_LENGTH_MESSAGE)
    private String password;

    private CustomerDeleteRequest() {
    }

    public CustomerDeleteRequest(final String password) {
        this.password = password;
    }

    public CustomerDeleteServiceRequest toServiceRequest(final Long id) {
        return new CustomerDeleteServiceRequest(id, password);
    }

    public String getPassword() {
        return password;
    }
}
