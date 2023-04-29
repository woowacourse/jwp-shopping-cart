package cart.dto.request.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class MemberSignupRequest {
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 비어있으면 안 됩니다.")
    @Length(
            max = 30,
            message = "이메일 최대 {max}자리 보다 작아야 합니다."
    )
    @Pattern(regexp = "^[^:]*$", message = "\":\"가 포함될 수 없습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 비어있으면 안 됩니다.")
    @Length(
            min = 4,
            max = 20,
            message = "비밀번호는 {min}자리에서 {max}자리 사이여야 합니다."
    )
    @Pattern(regexp = "^[^:]*$", message = "\":\"가 포함될 수 없습니다.")
    private String password;

    public MemberSignupRequest(String email, String password) {
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
