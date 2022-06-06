package woowacourse.auth.dto;

import javax.validation.constraints.NotBlank;

public class PasswordRequest {

    @NotBlank
    private String password;

    private PasswordRequest() {
    }

    public PasswordRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
