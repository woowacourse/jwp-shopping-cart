package cart.domain.user.service;

import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import cart.domain.user.exception.VerifyUserException;
import cart.domain.user.usecase.BasicAuthorizationCartUserUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class BasicAuthorizationCartUserService implements BasicAuthorizationCartUserUseCase {

    private final CartUserRepository cartUserRepository;

    public BasicAuthorizationCartUserService(final CartUserRepository cartUserRepository) {
        this.cartUserRepository = cartUserRepository;
    }

    public void verifyCartUser(final String cartUserEmail, final String password) {
        final CartUser cartUser = cartUserRepository.findByEmail(cartUserEmail);

        if (cartUser.getPassword().equals(password)) {
            return;
        }

        throw new VerifyUserException();
    }
}
