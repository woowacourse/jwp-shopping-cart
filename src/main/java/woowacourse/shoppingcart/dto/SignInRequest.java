package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SignInRequest {
    @NotBlank(message = "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.")
    private final String password;
    @NotNull(message = "[ERROR] 올바른 이메일이 아닙니다.")
    @Email(message = "[ERROR] 올바른 이메일이 아닙니다.", regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
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
