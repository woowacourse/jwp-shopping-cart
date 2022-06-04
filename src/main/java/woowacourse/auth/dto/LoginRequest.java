package woowacourse.auth.dto;

import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
