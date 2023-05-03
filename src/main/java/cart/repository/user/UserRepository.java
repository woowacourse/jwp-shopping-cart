package cart.repository.user;

import cart.domain.user.User;

public interface UserRepository {

    User save(User user);

    boolean existsByEmailAndPassword(String email, String password);

    User findUserByEmail(String email);
}
