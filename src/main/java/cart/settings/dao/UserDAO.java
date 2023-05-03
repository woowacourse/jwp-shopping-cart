package cart.settings.dao;

import cart.settings.domain.User;
import java.util.List;

public interface UserDAO {
    
    void insert(User user);
    
    boolean isExist(final String email);
    
    boolean isCorrectPassword(User user);
    
    User findByEmail(final String email);
    
    List<User> findAll();
}
