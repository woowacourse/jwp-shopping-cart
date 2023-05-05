package cart.settings.dao;

import cart.settings.domain.User;
import cart.settings.dto.UserRequestDTO;

import java.util.List;

public interface UserDAO {

    boolean isExist(UserRequestDTO userRequestDTO);

    User create(UserRequestDTO userRequestDTO);

    User find(UserRequestDTO userRequestDTO);

    List<User> findAll();

    void delete(User user);
}
