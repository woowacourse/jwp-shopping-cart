package cart.controller.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserRequest {

    @NotNull(message = "이메일은 빈 값일 수 없습니다.")
    @NotEmpty(message = "이메일은 빈 값일 수 없습니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private final String email;
    @NotNull(message = "비밀번호는 빈 값일 수 없습니다.")
    @NotEmpty(message = "비밀번호는 빈 값일 수 없습니다.")
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
