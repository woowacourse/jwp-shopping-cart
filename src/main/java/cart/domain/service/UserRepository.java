package cart.domain.service;

import cart.domain.user.User;

import java.util.List;

public interface UserRepository {
    Long save(User user);

    List<User> findAll();
}
