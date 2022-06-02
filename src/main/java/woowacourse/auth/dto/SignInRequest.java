package woowacourse.auth.dto;

import javax.validation.constraints.Email;
import woowacourse.auth.support.PasswordCheck;

public class SignInRequest {

    @Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "Email에 한글과 공백은 입력할 수 없습니다.")
    private String email;
    @PasswordCheck
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
