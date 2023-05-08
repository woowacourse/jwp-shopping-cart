package cart.service.dto;

import cart.domain.user.User;

public class UserDto {

    private final String email;
    private final String password;

    public UserDto(User user) {
        email = user.getEmail();
        password = user.getPassword();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
