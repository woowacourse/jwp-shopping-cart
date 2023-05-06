package cart.auth.dao;

import cart.auth.domain.User;
import cart.auth.dto.UserInfo;
import java.util.List;

public interface UserDAO {
    
    boolean isExist(UserInfo userInfo);
    
    User create(UserInfo userInfo);
    
    User find(UserInfo userInfo);
    
    List<User> findAll();
    
    void delete(User user);
}
