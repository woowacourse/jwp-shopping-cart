package cart.dto;

import cart.entity.UserEntity;

public class UserResponseDto {
    private final String email;
    private final String password;

    public UserResponseDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserResponseDto from(final UserEntity user) {
        return new UserResponseDto(user.getEmail(), user.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
