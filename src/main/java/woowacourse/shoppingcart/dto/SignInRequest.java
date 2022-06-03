package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.validation.PasswordCheck;

import javax.validation.constraints.*;

public class SignInRequest {
    private static final String INVALID_PASSWORD = "[ERROR] 비밀번호는 빈 값일 수 없습니다.";
    private static final String INVALID_PASSWORD_SIZE = "[ERROR] 비밀번호는 최소 6자 이상이어야 합니다.";
    private static final String INVALID_EMAIL = "[ERROR] 올바른 이메일이 아닙니다.";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    public static final String INVALID_EMAIL_SIZE = "[ERROR] 이메일은 최대 64자 입니다.";
    @NotNull(message = INVALID_PASSWORD)
    @PasswordCheck()
    @Size(min = 6, message = INVALID_PASSWORD_SIZE)
    private final String password;
    @NotNull(message = INVALID_EMAIL)
    @Email(message = INVALID_EMAIL, regexp = EMAIL_REGEX)
    @Size(max = 64, message = INVALID_EMAIL_SIZE)
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
