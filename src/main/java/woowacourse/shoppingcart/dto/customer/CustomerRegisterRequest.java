package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import woowacourse.shoppingcart.application.dto.CustomerSaveServiceRequest;
import woowacourse.shoppingcart.domain.customer.RawPassword;

public class CustomerRegisterRequest {

    private static final String EMPTY_NAME_MESSAGE = "이름은 공백일 수 없습니다.";
    private static final String EMPTY_EMAIL_MESSAGE = "이메일은 공백일 수 없습니다.";
    private static final String INVALID_EMAIL_FORMAT_MESSAGE = "이메일 형식이 올바르지 않습니다.";
    private static final String INVALID_PASSWORD_LENGTH_MESSAGE = "비밀번호는 8자 이상이어야 합니다.";

    @NotBlank(message = EMPTY_NAME_MESSAGE)
    private String name;

    @NotBlank(message = EMPTY_EMAIL_MESSAGE)
    @Email(message = INVALID_EMAIL_FORMAT_MESSAGE)
    private String email;

    @NotBlank(message = INVALID_PASSWORD_LENGTH_MESSAGE)
    @Size(min = RawPassword.MIN_RAW_VALUE_LENGTH, message = INVALID_PASSWORD_LENGTH_MESSAGE)
    private String password;

    private CustomerRegisterRequest() {
    }

    public CustomerRegisterRequest(final String name, final String email, final String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public CustomerSaveServiceRequest toServiceDto() {
        return new CustomerSaveServiceRequest(name, email, password);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
