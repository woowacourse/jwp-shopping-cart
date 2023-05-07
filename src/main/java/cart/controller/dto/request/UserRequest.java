package cart.controller.dto.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserRequest {

    @NotEmpty(message = "이메일은 빈 값일 수 없습니다.")
    @Length(max = 50, message = "이메일은 50자 이하로 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private final String email;
    @NotEmpty(message = "비밀번호는 빈 값일 수 없습니다.")
    @Length(max = 20, message = "비밀번호는 20자 이하로 입력해주세요.")
    private final String password;

    public UserRequest(String email, String password) {
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
