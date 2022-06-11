package woowacourse.member.dto.request;

import javax.validation.constraints.NotBlank;

public class PasswordRequest {

    @NotBlank
    private String password;

    public PasswordRequest() {
    }

    public PasswordRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
