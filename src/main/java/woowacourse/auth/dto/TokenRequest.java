package woowacourse.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import woowacourse.auth.application.dto.LoginServiceRequest;
import woowacourse.shoppingcart.domain.customer.Password;

public class TokenRequest {

    private static final String EMPTY_EMAIL_MESSAGE = "이메일은 공백일 수 없습니다.";
    private static final String INVALID_EMAIL_FORMAT_MESSAGE = "이메일 형식이 올바르지 않습니다.";
    private static final String INVALID_PASSWORD_LENGTH_MESSAGE = "비밀번호는 8자 이상이어야 합니다.";

    @NotBlank(message = EMPTY_EMAIL_MESSAGE)
    @Email(message = INVALID_EMAIL_FORMAT_MESSAGE)
    private String email;

    @NotBlank(message = INVALID_PASSWORD_LENGTH_MESSAGE)
    @Size(min = Password.MIN_RAW_VALUE_LENGTH, message = INVALID_PASSWORD_LENGTH_MESSAGE)
    private String password;

    private TokenRequest() {
    }

    public TokenRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LoginServiceRequest toServiceDto() {
        return new LoginServiceRequest(email, password);
    }
}
