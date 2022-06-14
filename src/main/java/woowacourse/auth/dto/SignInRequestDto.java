package woowacourse.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignInRequestDto {
    @Email(message = "이메일 형식을 지켜야합니다.")
    @NotBlank(message = "이메일에는 공백이 들어가면 안됩니다.")
    @Size(min = 8, max = 50, message = "이메일은 8자 이상 50자 이하여야합니다.")
    private String email;

    @NotBlank(message = "비밀번호에는 공백이 들어가면 안됩니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야합니다.")
    private String password;

    private SignInRequestDto() {
    }

    public SignInRequestDto(final String email, final String password) {
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
