package cart.entiy.user;

import cart.domain.user.User;

public class UserEntity {

    private final UserEntityId userId;
    private final String email;
    private final String password;

    public UserEntity(final UserEntityId userId, final String email, final String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public UserEntity(final Long userId, final UserEntity userEntity) {
        this(new UserEntityId(userId), userEntity.email, userEntity.password);
    }

    public static UserEntity from(final User user) {
        return new UserEntity(
                new UserEntityId(user.getUserId()),
                user.getEmail().getValue(),
                user.getPassword().getValue());
    }

    public User toDomain() {
        return new User(userId.getValue(), email, password);
    }

    public UserEntityId getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
