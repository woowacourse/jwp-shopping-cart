package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.common.exception.UnauthorizedException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.PasswordEncoder;
import woowacourse.shoppingcart.dto.TokenResponse;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class AuthService {

    private static final String LOGIN_FAILED_ERROR = "로그인이 불가능합니다.";

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CustomerDao customerDao;

    public AuthService(JwtTokenProvider jwtTokenProvider,
                       PasswordEncoder passwordEncoder, CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.customerDao = customerDao;
    }

    public TokenResponse login(SignInRequest signinRequest) {
        String account = signinRequest.getAccount();
        CustomerEntity customerEntity = customerDao.findByAccount(account)
                .orElseThrow(InvalidCustomerException::new);

        if (!passwordEncoder.match(signinRequest.getPassword(), customerEntity.getPassword())) {
            throw new UnauthorizedException(LOGIN_FAILED_ERROR);
        }

        return new TokenResponse(
                jwtTokenProvider.createToken(String.valueOf(customerEntity.getId())));
    }
}
