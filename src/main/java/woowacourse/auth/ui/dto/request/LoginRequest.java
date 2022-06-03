package woowacourse.auth.ui.dto.request;

import javax.validation.constraints.NotBlank;
import woowacourse.auth.application.dto.request.LoginServiceRequest;

public class LoginRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LoginServiceRequest toServiceDto() {
        return new LoginServiceRequest(email, password);
    }
}
