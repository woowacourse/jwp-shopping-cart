package woowacourse.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.exception.AuthorizationException;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.domain.customer.PlainPassword;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.support.Encryptor;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final Encryptor encryptor;
    private final CustomerDao customerDao;

    public AuthService(final JwtTokenProvider jwtTokenProvider, final Encryptor encryptor,
                       final CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.encryptor = encryptor;
        this.customerDao = customerDao;
    }

    public TokenResponse login(final TokenRequest request) {
        final Password password = encryptor.encrypt(new PlainPassword(request.getPassword()));
        if (customerDao.existsByNameAndPassword(new UserName(request.getUserName()), password)) {
            final String token = jwtTokenProvider.createToken(request.getUserName());
            return new TokenResponse(token);
        }
        throw new AuthorizationException("로그인에 실패했습니다😤");
    }
}
