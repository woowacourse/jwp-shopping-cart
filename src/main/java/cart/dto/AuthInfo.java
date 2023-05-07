package cart.dto;

import javax.validation.constraints.NotEmpty;

public class AuthInfo {
    @NotEmpty
    private final String email;
    @NotEmpty
    private final String password;

    public AuthInfo(String email, String password) {
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
