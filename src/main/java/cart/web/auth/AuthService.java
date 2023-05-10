package cart.web.auth;

import cart.domain.user.Email;
import cart.domain.user.Password;
import cart.domain.user.UserRepository;
import cart.exception.AuthorizationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authenticate(UserInfo userInfo) {
        Email email = Email.from(userInfo.getEmail());
        Password password = Password.from(userInfo.getPassword());

        if (userRepository.isRegisteredUser(email, password)) {
            return;
        }

        throw new AuthorizationException();
    }
}
