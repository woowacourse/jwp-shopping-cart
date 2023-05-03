package cart.settings.service;

import cart.settings.dao.UserDAO;
import cart.settings.domain.User;
import cart.settings.dto.ResponseUserDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    
    private final UserDAO userDAO;
    
    public SettingsService(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public List<ResponseUserDTO> findAllUsers() {
        final List<User> users = this.userDAO.findAll();
        return users.stream()
                .map(ResponseUserDTO::from)
                .collect(Collectors.toList());
    }
}
