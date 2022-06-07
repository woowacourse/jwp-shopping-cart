package woowacourse.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class TokenRequest {

    @Email(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank
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
}
