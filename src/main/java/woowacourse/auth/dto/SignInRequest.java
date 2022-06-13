package woowacourse.auth.dto;

import javax.validation.constraints.NotBlank;
import woowacourse.auth.domain.SignIn;

public class SignInRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    private SignInRequest() {
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

    public SignIn toSignIn() {
        return new SignIn(email, password);
    }
}
