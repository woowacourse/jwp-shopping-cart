package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SignInRequest {
    private static final String INVALID_PASSWORD = "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.";
    private static final String INVALID_EMAIL = "[ERROR] 올바른 이메일이 아닙니다.";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    @NotBlank(message = INVALID_PASSWORD)
    private final String password;
    @NotNull(message = INVALID_EMAIL)
    @Email(message = INVALID_EMAIL, regexp = EMAIL_REGEX)
    private final String email;

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
