package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignInDto {
    @Email
    @NotBlank
    @Size(min = 8, max = 50)
    private final String email;

    @NotBlank
    @Size(min = 8, max = 20)
    private final String password;

    public SignInDto(final String email, final String password) {
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
