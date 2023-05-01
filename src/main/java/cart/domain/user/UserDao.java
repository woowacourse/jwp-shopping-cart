package cart.domain.user;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    List<User> findAll();
}
