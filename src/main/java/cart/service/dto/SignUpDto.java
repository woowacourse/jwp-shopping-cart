package cart.service.dto;

import cart.domain.Email;
import cart.domain.Password;

public class SignUpDto {

    private String email;
    private String password;

    public SignUpDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public Email getEmailDomain() {
        return new Email(email);
    }

    public Password getPasswordDomain() {
        return new Password(password);
    }
}
