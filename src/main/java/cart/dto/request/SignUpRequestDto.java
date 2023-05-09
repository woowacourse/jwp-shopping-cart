package cart.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class SignUpRequestDto {

    @NotNull(message = "email은 null이면 안됩니다.")
    @Email(message = "이메일 형식이어야 합니다.")
    private final String email;
    @NotNull(message = "password는 null이면 안됩니다.")
    private final String password;
    private final String name;

    public SignUpRequestDto(final String email, final String password, final String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
