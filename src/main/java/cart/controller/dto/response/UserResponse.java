package cart.controller.dto.response;

import cart.service.dto.UserDto;

public class UserResponse {

    private final String email;
    private final String password;

    private UserResponse(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static UserResponse from(UserDto userDto) {
        return new UserResponse(userDto.getEmail(), userDto.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
