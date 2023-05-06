package cart.auth.dao;

import cart.auth.domain.User;
import cart.auth.dto.UserRequestDTO;
import java.util.List;

public interface UserDAO {
    
    boolean isExist(UserRequestDTO userRequestDTO);
    
    User create(UserRequestDTO userRequestDTO);
    
    User find(UserRequestDTO userRequestDTO);
    
    List<User> findAll();
    
    void delete(User user);
}
