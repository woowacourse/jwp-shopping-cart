package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.domain.Token;
import woowacourse.auth.dto.request.TokenRequest;
import woowacourse.auth.dto.response.TokenResponse;
import woowacourse.common.exception.AuthenticationException;

@Service
public class AuthService {

    private final CustomerDao customerDao;
    private final JwtTokenService tokenService;

    public AuthService(CustomerDao customerDao, JwtTokenService tokenService) {
        this.customerDao = customerDao;
        this.tokenService = tokenService;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        Customer customer = findValidCustomer(tokenRequest.getUsername(), tokenRequest.getPassword());
        Token accessToken = tokenService.generateToken(customer.getUsername());
        return new TokenResponse(accessToken.getValue());
    }

    private Customer findValidCustomer(String username, String password) {
        Customer customer = customerDao.findByUserName(username)
                .orElseThrow(AuthenticationException::ofLoginFailure);
        if (customer.hasDifferentPassword(password)) {
            throw AuthenticationException.ofLoginFailure();
        }
        return customer;
    }

    public Customer findCustomerByToken(String token) {
        String username = tokenService.extractPayload(new Token(token));
        return customerDao.findByUserName(username)
                .orElseThrow(AuthenticationException::ofInvalidToken);
    }
}
