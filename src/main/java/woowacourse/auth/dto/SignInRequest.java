package woowacourse.auth.dto;

import woowacourse.auth.domain.SignIn;
import woowacourse.auth.support.EmailCheck;
import woowacourse.auth.support.PasswordCheck;

public class SignInRequest {

    @EmailCheck
    private String email;
    @PasswordCheck
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
