package cart.entiy.user;

import cart.domain.user.User;
import cart.domain.user.UserId;

public class UserEntity {

    private final UserId userId;
    private final String email;
    private final String password;

    public UserEntity(final UserId userId, final String email, final String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public UserEntity(final Long userId, final UserEntity userEntity) {
        this(new UserId(userId), userEntity.email, userEntity.password);
    }


    public static UserEntity from(final User user) {
        return new UserEntity(
                user.getUserId(),
                user.getEmail().getValue(),
                user.getPassword().getValue());
    }

    public User toDomain() {
        return new User(userId.getValue(), email, password);
    }

    public UserId getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
