package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.LoginCustomer;
import woowacourse.shoppingcart.exception.InvalidCustomerLoginException;
import woowacourse.shoppingcart.exception.InvalidTokenException;
import woowacourse.shoppingcart.util.HashTool;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public TokenResponse createToken(TokenRequest tokenRequest) {
        if (!customerDao.checkValidLogin(tokenRequest.getLoginId(), HashTool.hashing(tokenRequest.getPassword()))) {
            throw new InvalidCustomerLoginException();
        }

        String token = jwtTokenProvider.createToken(tokenRequest.getLoginId());
        Customer customer = customerDao.findByLoginId(tokenRequest.getLoginId());
        return new TokenResponse(token, customer.getUsername());
    }

    public LoginCustomer findCustomerByToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
        String payload = jwtTokenProvider.getPayload(token);

        return new LoginCustomer(customerDao.findByLoginId(payload));
    }
}
