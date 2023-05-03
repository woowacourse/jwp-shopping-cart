package cart.event.user;

import cart.domain.user.User;

public class UserRegisteredEvent {

    private final User user;

    public UserRegisteredEvent(final User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
