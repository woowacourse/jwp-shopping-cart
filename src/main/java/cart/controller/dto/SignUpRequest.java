package cart.controller.dto;

import cart.service.dto.SignUpDto;
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

    public SignUpDto toSignUpDto() {
        return new SignUpDto(email, password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
