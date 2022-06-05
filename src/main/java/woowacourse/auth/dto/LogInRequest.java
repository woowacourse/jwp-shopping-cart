package woowacourse.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LogInRequest {

    @NotBlank
    @Email
    @Size(max = 64)
    private String email;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Size(min = 6)
    private String password;

    public LogInRequest() {
    }

    public LogInRequest(String email, String password) {
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
