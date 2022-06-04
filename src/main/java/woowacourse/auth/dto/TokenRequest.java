package woowacourse.auth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TokenRequest {

    @NotBlank
    @Size(min=4, max=20)
    private String username;
    @NotBlank
    @Size(min=8, max=20)
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(String username, String password) {
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
