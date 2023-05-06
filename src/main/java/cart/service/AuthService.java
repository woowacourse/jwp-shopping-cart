package cart.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import cart.domain.user.User;
import cart.infrastructure.BasicAuthorizationExtractor;
import cart.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
    private final UserRepository userRepository;

    public AuthService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(final HttpServletRequest httpServletRequest) {
        final User user = basicAuthorizationExtractor.extract(httpServletRequest);
        validateLogin(user);
        return user;
    }

    private void validateLogin(final User user) {
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
