package cart.domain.user;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao {

    List<User> findAll();

    Optional<User> findUserByEmail(String email);
}
