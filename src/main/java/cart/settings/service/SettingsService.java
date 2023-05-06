package cart.settings.service;

import cart.auth.dao.UserDAO;
import cart.auth.domain.User;
import cart.auth.dto.UserResponseDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    
    private final UserDAO userDAO;
    
    public SettingsService(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public List<UserResponseDTO> findAllUsers() {
        final List<User> users = this.userDAO.findAll();
        return users.stream()
                .map(UserResponseDTO::from)
                .collect(Collectors.toList());
    }
}
