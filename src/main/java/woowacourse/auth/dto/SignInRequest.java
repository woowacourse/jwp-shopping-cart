package woowacourse.auth.dto;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import woowacourse.auth.support.PasswordCheck;

public class SignInRequest {

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @Length(max = 64)
    private String email;

    @PasswordCheck
    @Length(min = 6)
    private String password;

    public SignInRequest() {
    }

    public SignInRequest(String email, String password) {
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
