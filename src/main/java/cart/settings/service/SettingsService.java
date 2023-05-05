package cart.settings.service;

import cart.settings.dao.UserDAO;
import cart.settings.domain.User;
import cart.settings.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
