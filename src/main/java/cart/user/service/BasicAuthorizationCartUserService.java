package cart.user.service;

import cart.exception.NoSuchDataExistException;
import cart.exception.VerifyUserException;
import cart.user.domain.CartUser;
import cart.user.domain.CartUserRepository;
import cart.user.usecase.BasicAuthorizationCartUserUseCase;
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
        final CartUser cartUser = cartUserRepository.findByEmail(cartUserEmail)
                .orElseThrow(() -> new NoSuchDataExistException("가입 되지 않는 사용자 이메일 입니다."));

        if (cartUser.getPassword().equals(password)) {
            return;
        }

        throw new VerifyUserException("유효하지 않은 사용자 인증 정보 입니다.");
    }
}
