package cart.dto.request;

import javax.validation.constraints.NotNull;

public class LoginRequestDto {

    @NotNull(message = "email은 null이면 안됩니다.")
    private final String email;
    @NotNull(message = "password는 null이면 안됩니다.")
    private final String password;

    public LoginRequestDto(final String email, final String password) {
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
