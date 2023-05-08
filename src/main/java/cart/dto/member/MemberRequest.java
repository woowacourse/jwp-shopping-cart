package cart.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberRequest {

    @NotBlank(message = "이메일은 빈 칸이 될 수 없습니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private final String email;
    @NotBlank(message = "비밀번호는 빈 칸이 될 수 없습니다")
    private final String password;

    public MemberRequest(final String email, final String password) {
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
