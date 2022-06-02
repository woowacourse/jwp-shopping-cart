package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.validation.PasswordCheck;
import woowacourse.shoppingcart.validation.UserNameCheck;

import javax.validation.constraints.*;

public class SignUpRequest {

    private static final String INVALID_USERNAME_EMPTY = "[ERROR] 이름은 빈 값일 수 없습니다.";
    private static final String INVALID_USERNAME_SIZE = "[ERROR] 이름은 최대 32자 입니다.";
    private static final String INVALID_EMAIL = "[ERROR] 올바른 이메일이 아닙니다.";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private static final String INVALID_PASSWORD = "[ERROR] 비밀번호는 빈 값일 수 없습니다.";
    private static final String INVALID_PASSWORD_SIZE = "[ERROR] 비밀번호는 최소 6자 이상이어야 합니다.";
    public static final String INVALID_EMAIL_SIZE = "[ERROR] 이메일은 최대 64자 입니다.";

    @NotEmpty(message = INVALID_USERNAME_EMPTY)
    @Size(max = 32, message = INVALID_USERNAME_SIZE)
    @UserNameCheck()
    private String username;
    @NotNull(message = INVALID_EMAIL)
    @Size(max = 64, message = INVALID_EMAIL_SIZE)
    @Email(message = INVALID_EMAIL, regexp = EMAIL_REGEX)
    private String email;
    @NotNull(message = INVALID_PASSWORD)
    @PasswordCheck()
    @Size(min = 6, message = INVALID_PASSWORD_SIZE)
    private String password;

    public SignUpRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
