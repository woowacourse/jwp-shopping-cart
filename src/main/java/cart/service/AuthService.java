package cart.service;

import cart.domain.User;
import cart.dto.BasicCredentials;
import cart.exception.InvalidPasswordException;
import cart.exception.UserNotFoundException;
import cart.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authenticateUser(BasicCredentials credentials) {
        String email = credentials.getEmail();
        User foundUser = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        String password = credentials.getPassword();
        matchPassword(foundUser, password);
    }

    private void matchPassword(User foundUser, String password) {
        if (!foundUser.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }
    }
}
