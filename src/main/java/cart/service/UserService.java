package cart.service;

import cart.domain.User;
import cart.exception.InvalidPasswordException;
import cart.exception.UserNotFoundException;
import cart.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public void authorizeUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }
    }
}
