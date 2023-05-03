package cart.dto;

import cart.persistence.entity.UserEntity;

public class UserResponse {
    private final String email;

    private final String password;

    public UserResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserResponse from(final UserEntity userEntity) {
        return new UserResponse(userEntity.getEmail(), userEntity.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
