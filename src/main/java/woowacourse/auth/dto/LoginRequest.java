package woowacourse.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class LoginRequest {

    @Email(message = "이메일 양식이 잘못 되었습니다.")
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,20}", message = "비밀번호 양식이 잘못 되었습니다.")
    private String password;

    private LoginRequest() {
    }

    public LoginRequest(final String email, final String password) {
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
