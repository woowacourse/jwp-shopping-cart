package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpDto {

    @Email(message = "이메일 형식을 지켜야합니다.")
    @NotBlank(message = "이메일에는 공백이 들어가면 안됩니다.")
    @Size(min = 8, max = 50, message = "이메일은 8자 이상 50자 이하여야합니다.")
    private final String email;
    @NotBlank(message = "비밀번호에는 공백이 들어가면 안됩니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야합니다.")
    private final String password;
    @NotBlank(message = "닉네임에는 공백이 들어가면 안됩니다.")
    @Size(min = 1, max = 10, message = "닉네임은 1자 이상 10자 이하여야합니다.")
    private final String username;

    public SignUpDto(final String email, final String password, final String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
