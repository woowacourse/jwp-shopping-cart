package cart.domain.user;

import java.util.List;

public interface UserRepository {
    Long save(User user);

    List<User> findAll();

    User findById(Long id);

    User findByEmailAndPassword(Email email, Password password);

    boolean isRegisteredUser(Email email, Password password);

    void deleteById(Long id);
}
