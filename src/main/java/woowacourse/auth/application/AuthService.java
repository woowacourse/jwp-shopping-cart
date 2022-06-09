package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.exception.InvalidLoginFormException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dao.AccountDao;
import woowacourse.shoppingcart.domain.Account;
import woowacourse.shoppingcart.exception.InvalidAccountException;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final AccountDao accountDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(AccountDao accountDao, JwtTokenProvider jwtTokenProvider) {
        this.accountDao = accountDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String createToken(TokenRequest tokenRequest) {
        Account account = accountDao.findByEmail(tokenRequest.getEmail())
            .orElseThrow(InvalidAccountException::new);

        String encryptPassword = PasswordEncoder.encrypt(tokenRequest.getPassword());
        if (!account.isValidPassword(encryptPassword)) {
            throw new InvalidLoginFormException();
        }

        return jwtTokenProvider.createToken(tokenRequest.getEmail());
    }
}
