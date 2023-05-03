package cart.service.dto;

import javax.validation.constraints.NotBlank;

public class SignUpRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public SignUpRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public SignUpRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
