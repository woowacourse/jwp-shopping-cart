package cart.service.dto;

import cart.domain.Email;
import cart.domain.Password;
import javax.validation.constraints.NotBlank;

public class SignUpRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public SignUpRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SignUpRequest() {
    }

    public Email getEmail() {
        return new Email(email);
    }

    public Password getPassword() {
        return new Password(password);
    }
}
