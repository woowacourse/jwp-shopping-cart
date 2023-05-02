package cart.entiy;

import cart.domain.user.User;

public class UserEntity {

    private final String email;
    private final String password;

    public UserEntity(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static UserEntity from(final User user) {
        return new UserEntity(
                user.getEmail().getValue(),
                user.getPassword()
        );
    }

    public User toDomain() {
        return new User(email, password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
