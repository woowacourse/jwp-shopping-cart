package cart.event.user;

import cart.domain.user.User;
import cart.domain.user.UserId;

public class UserRegisteredEvent {

    private final UserId userId;

    public UserRegisteredEvent(final User user) {
        userId = user.getUserId();
    }

    public UserId getUser() {
        return userId;
    }
}
