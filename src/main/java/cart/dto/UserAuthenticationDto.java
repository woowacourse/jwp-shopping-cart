package cart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserAuthenticationDto {

    @NotNull(message = "email이 null이면 안됩니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private final String email;
    @NotNull(message = "password가 null이면 안됩니다.")
    @Min(value = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private final String password;

    public UserAuthenticationDto(final String email, final String password) {
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
