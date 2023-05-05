package cart.entity;

import java.util.List;

public interface UserRepository {
    Long save(User user);

    User findById(Long id);

    List<User> findAll();

    void updateById(User user, Long id);

    void deleteById(Long id);
}
