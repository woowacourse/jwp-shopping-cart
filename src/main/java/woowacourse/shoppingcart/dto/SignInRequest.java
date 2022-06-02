package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import woowacourse.util.PasswordCheck;

public class SignInRequest {
    private static final String INVALID_PASSWORD = "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.";
    private static final String INVALID_LENGTH_PASSWORD = "[ERROR] 비밀번호는 6자 이상이어야 합니다.";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private static final String BLANK_EMAIL = "[ERROR] 이메일은 공백 또는 빈 값일 수 없습니다.";
    private static final String INVALID_PATTERN_EMAIL = "[ERROR] 올바른 이메일 형식이 아닙니다.";
    private static final String INVALID_LENGTH_EMAIL = "[ERROR] 이메일의 길이는 64자를 넘을수 없습니다.";

    @NotNull(message = BLANK_EMAIL)
    @Email(message = INVALID_PATTERN_EMAIL, regexp = EMAIL_REGEX)
    @Size(max = 64, message = INVALID_LENGTH_EMAIL)
    private final String email;

    @NotBlank(message = INVALID_PASSWORD)
    @Size(min = 6, message = INVALID_LENGTH_PASSWORD)
    @PasswordCheck
    private final String password;


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
