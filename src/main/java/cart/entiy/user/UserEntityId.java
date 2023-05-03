package cart.entiy.user;

import cart.domain.user.UserId;

public class UserEntityId {

    private final Long id;

    public UserEntityId(final Long id) {
        this.id = id;
    }

    public UserEntityId(final UserId userId) {
        this(userId.getValue());
    }

    public static UserEntityId from(final UserId userId) {
        return new UserEntityId(userId);
    }

    public Long getValue() {
        return id;
    }
}
