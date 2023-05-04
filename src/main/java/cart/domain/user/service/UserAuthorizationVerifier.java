package cart.domain.user.service;

import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserAuthorizationVerifier {

    private final CartUserRepository cartUserRepository;

    public UserAuthorizationVerifier(final CartUserRepository cartUserRepository) {
        this.cartUserRepository = cartUserRepository;
    }

    public void verifyCartUser(final String cartUserEmail, final String password) {
        final CartUser cartUser = cartUserRepository.findByEmail(cartUserEmail);

        if (cartUser.getPassword().equals(password)) {
            return;
        }

        throw new IllegalArgumentException(); //TODO: Exception
    }
}
