package cart.service.user;

import cart.domain.user.Email;
import cart.domain.user.Password;
import cart.domain.user.User;

import java.util.List;

public interface UserRepository {
    Long save(User user);

    List<User> findAll();

    User findById(Long id);

    User findByEmailAndPassword(Email email, Password password);

    boolean isRegisteredUser(Email email, Password password);

    void deleteById(Long id);
}
