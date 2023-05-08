package cart.dto;

import cart.persistence.entity.UserEntity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
