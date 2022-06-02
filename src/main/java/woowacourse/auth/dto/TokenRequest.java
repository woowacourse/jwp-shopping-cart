package woowacourse.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class TokenRequest {

    @Email(message = "잘못된 이메일 형식입니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,20}$", message = "잘못된 비밀번호 형식입니다.")
    private String password;

    public TokenRequest() {
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
