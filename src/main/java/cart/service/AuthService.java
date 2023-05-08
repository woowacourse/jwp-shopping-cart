package cart.service;

import java.util.Optional;

import cart.domain.user.User;
import cart.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateLogin(final User user) {
        final Optional<User> realUser = userRepository.findByEmail(user.getEmail());
        if (realUser.isEmpty()) {
            throw new EmailNotFoundException();
        }
        if (realUser.get().equals(user)) {
            return;
        }
        throw new PasswordMismatchException();
    }
}
