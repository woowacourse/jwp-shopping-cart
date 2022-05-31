package woowacourse.auth.ui.dto;

import javax.validation.constraints.NotBlank;
import woowacourse.auth.application.dto.TokenCreateRequest;

public class TokenRequest {

    @NotBlank(message = "[ERROR] 이메일은 공백일 수 없습니다.")
    private String email;
    @NotBlank(message = "[ERROR] 비밀번호는 공백일 수 없습니다.")
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public TokenCreateRequest toServiceRequest() {
        return new TokenCreateRequest(email, password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
