package cart.domain.user.service;

import cart.domain.user.CartUser;
import cart.domain.user.CartUserRepository;
import cart.domain.user.usecase.BasicAuthorizationCartUserUseCase;
import cart.web.exception.NoSuchDataExistException;
import cart.web.exception.VerifyUserException;
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
