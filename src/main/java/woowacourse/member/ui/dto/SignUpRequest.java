package woowacourse.member.ui.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignUpRequest {

    @NotBlank(message = "이메일은 빈 값일 수 없습니다.")
    @Email(message = "올바르지 않은 형식의 이메일입니다.")
    private String email;
    @NotBlank(message = "이름은 빈 값일 수 없습니다.")
    private String name;
    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다.")
    private String password;

    public SignUpRequest() {
    }

    public SignUpRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
