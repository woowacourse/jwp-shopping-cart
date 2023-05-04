package cart.factory;

import cart.domain.User;

public class UserFactory {
    public static User createUser(String email) {
        return new User(1L, email, "1234");
    }
}
