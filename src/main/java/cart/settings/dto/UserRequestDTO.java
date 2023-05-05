package cart.settings.dto;

import cart.settings.domain.Email;
import cart.settings.domain.Password;

public class UserRequestDTO {

    private final Email email;
    private final Password password;

    public UserRequestDTO(final Email email, final Password password) {
        this.email = email;
        this.password = password;
    }

    public static UserRequestDTO of(final String email, final String password) {
        return new UserRequestDTO(new Email(email), new Password(password));
    }

    public Email getEmail() {
        return this.email;
    }

    public Password getPassword() {
        return this.password;
    }
}
