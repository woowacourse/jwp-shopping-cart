package cart.auth.service;

import cart.auth.dao.UserDAO;
import cart.auth.domain.User;
import cart.auth.dto.UserInfo;
import cart.auth.dto.UserResponseDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final UserDAO userDAO;
    
    public AuthService(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public List<UserResponseDTO> findAllUsers() {
        final List<User> users = this.userDAO.findAll();
        return users.stream()
                .map(UserResponseDTO::from)
                .collect(Collectors.toList());
    }
    
    public UserResponseDTO findUser(final UserInfo userInfo) {
        final User user = this.userDAO.find(userInfo);
        return UserResponseDTO.from(user);
    }
}
