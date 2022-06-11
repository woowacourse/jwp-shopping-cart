package woowacourse.auth.ui.dto;

import woowacourse.auth.application.dto.TokenCreateRequest;

import javax.validation.constraints.NotBlank;

public class TokenRequest {

    @NotBlank(message = "[ERROR] 이메일은 공백일 수 없습니다.")
    private String email;
    @NotBlank(message = "[ERROR] 비밀번호는 공백일 수 없습니다.")
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(final String email, final String password) {
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

    @Override
    public String toString() {
        return "TokenRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
