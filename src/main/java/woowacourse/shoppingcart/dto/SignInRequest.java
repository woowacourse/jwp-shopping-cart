package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import woowacourse.shoppingcart.domain.customer.Password;

public class SignInRequest {
    private static final String INVALID_PASSWORD = "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private static final String BLANK_EMAIL = "[ERROR] 이메일은 공백 또는 빈 값일 수 없습니다.";
    private static final String INVALID_PATTERN_EMAIL = "[ERROR] 올바른 이메일 형식이 아닙니다.";

    @NotNull(message = BLANK_EMAIL)
    @Email(message = INVALID_PATTERN_EMAIL, regexp = EMAIL_REGEX)
    private final String email;

    @NotBlank(message = INVALID_PASSWORD)
    private final String password;

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Password toPassword() {
        return new Password(password);
    }

    public String getEmail() {
        return email;
    }
}
