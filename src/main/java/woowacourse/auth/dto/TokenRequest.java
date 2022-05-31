package woowacourse.auth.dto;

import woowacourse.auth.application.dto.LoginServiceRequest;

public class TokenRequest {

    private String email;
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(String email, String password) {
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
