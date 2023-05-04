package cart.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import cart.controller.LoginFailException;
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
        if (isValidLogin(user)) {
            return user;
        }
        throw new LoginFailException();
    }

    private boolean isValidLogin(final User user) {
        final Optional<User> realUser = userRepository.findByEmail(user.getEmail());
        return realUser.filter(user::equals).isPresent();
    }
}
