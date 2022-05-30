package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignUpRequest {

    @NotBlank(message = "[ERROR] 이름은 공백 또는 빈 값일 수 없습니다.")
    private String username;
    @NotBlank(message = "[ERROR] 이메일은 공백 또는 빈 값일 수 없습니다.")
    @Email(message = "[ERROR] 이메일 형식이 아닙니다.", regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    private String email;
    @NotBlank(message = "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.")
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
