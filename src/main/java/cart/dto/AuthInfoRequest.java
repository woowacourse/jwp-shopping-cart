package cart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AuthInfoRequest {

    @Email(message = "이메일 형식이 잘못되었습니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private final String password;

    public AuthInfoRequest(final String email, final String password) {
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
