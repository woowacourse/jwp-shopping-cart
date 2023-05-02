package cart.service;

import cart.domain.user.Email;
import cart.domain.user.User;
import cart.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidLogin(final String email, final String password) {
        final Email id = new Email(email);
        final User input = new User(id, password);
        final User user = userRepository.findByEmail(id);
        return input.equals(user);
    }
}