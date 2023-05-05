package cart.settings.dto;

import cart.settings.domain.User;

public class UserResponseDTO {

    private final String email;
    private final String password;

    public UserResponseDTO(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static UserResponseDTO from(final User user) {
        final String email = user.getEmail().getValue();
        final String password = user.getPassword().getValue();
        return new UserResponseDTO(email, password);
    }

    public static UserResponseDTO from(final String email, final String password) {
        return new UserResponseDTO(email, password);
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
