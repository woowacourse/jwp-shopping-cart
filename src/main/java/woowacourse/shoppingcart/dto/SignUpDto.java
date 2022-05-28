package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SignUpDto {

    @Email
    @NotBlank
    @Size(min = 8, max = 50)
    private final String email;
    @NotBlank
    @Size(min = 8, max = 20)
    private final String password;
    @NotBlank
    @Size(min = 1, max = 10)
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
