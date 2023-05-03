package cart.user.dao;

import cart.user.domain.User;
import java.util.List;

public interface UserDAO {
    
    void insert(User user);
    
    boolean isExist(final String email);
    
    boolean isCorrectPassword(User user);
    
    User findByEmail(final String email);
    
    List<User> findAll();
}
